package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.DiscordToken;
import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.UserDiscordInfo;
import fr.gravendev.gravendevback.entity.user.about.AboutIntro;
import fr.gravendev.gravendevback.model.discord.DiscordGuildMemberResponse;
import fr.gravendev.gravendevback.model.discord.DiscordTokensResponse;
import fr.gravendev.gravendevback.model.discord.DiscordUserResponse;
import fr.gravendev.gravendevback.model.user.UserModel;
import fr.gravendev.gravendevback.repository.AuthTokenRepository;
import fr.gravendev.gravendevback.repository.user.UserRepository;
import fr.gravendev.gravendevback.service.DiscordService;
import fr.gravendev.gravendevback.service.DiscordTokenService;
import fr.gravendev.gravendevback.service.user.about.AboutIntroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    private final DiscordService discordService;
    private final DiscordTokenService discordTokenService;
    private final UserDiscordInfoService userDiscordInfoService;
    private final UserRolesService userRolesService;
    private final UserTagsService userTagsService;
    private final AboutIntroService aboutIntroService;

    @Autowired
    public UserService(AuthTokenRepository authTokenRepository,
                       UserRepository userRepository,
                       DiscordService discordService,
                       DiscordTokenService discordTokenService,
                       UserDiscordInfoService userDiscordInfoService,
                       UserRolesService userRolesService,
                       UserTagsService userTagsService,
                       AboutIntroService aboutIntroService) {
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
        this.discordService = discordService;
        this.discordTokenService = discordTokenService;
        this.userDiscordInfoService = userDiscordInfoService;
        this.userRolesService = userRolesService;
        this.userTagsService = userTagsService;
        this.aboutIntroService = aboutIntroService;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String authToken) {
        return getUserFromAuthToken(authToken);
    }

    public Optional<User> getUser(Long discordId) {
        return getUserFromDiscordId(discordId);
    }

    public Optional<User> getUserFromAuthToken(String authToken) {
        return authTokenRepository.findByAuthToken(authToken)
                .map(AuthToken::getUser);
    }

    public Optional<User> getUserFromDiscordId(Long discordId) {
        return userRepository.findByDiscordInfoDiscordId(discordId);
    }

    public boolean hasRole(User user, String... codes) {
        return userRolesService.hasRole(user, codes);
    }

    public User create(AuthToken authToken, DiscordTokensResponse discordTokens, DiscordUserResponse discordUser) {
        DiscordGuildMemberResponse discordGuildMember = discordService.retrieveGuildMember(discordUser.id(), discordTokens.accessToken());

        DiscordToken discordToken = discordTokenService.create(discordTokens);
        UserDiscordInfo userDiscordInfo = userDiscordInfoService.create(discordUser, discordGuildMember);
        AboutIntro aboutIntro = aboutIntroService.create();

        User user = User.builder()
                .authTokens(Set.of(authToken))
                .discordToken(discordToken)
                .discordInfo(userDiscordInfo)
                .userRoles(Set.of())
                .userTags(Set.of())
                .aboutIntro(aboutIntro)
                .build();

        authToken.setUser(user);
        discordToken.setUser(user);
        userDiscordInfo.setUser(user);
        aboutIntro.setUser(user);

        user = userRepository.save(user);

        userRolesService.update(user, discordGuildMember.roles());

        return user;
    }

    public User update(User user, AuthToken authToken, DiscordTokensResponse discordTokens, DiscordUserResponse discordUser) {
        DiscordGuildMemberResponse discordGuildMember = discordService.retrieveGuildMember(discordUser.id(), discordTokens.accessToken());

        authToken.setUser(user);
        discordTokenService.update(user, discordTokens);
        userDiscordInfoService.update(user, discordUser, discordGuildMember);
        userRolesService.update(user, discordGuildMember.roles());

        return user;
    }

    public UserModel buildPrivateModel(User user) {
        return UserModel.builder()
                .discordId(String.valueOf(user.getDiscordInfo().getDiscordId()))
                .email(user.getDiscordInfo().getEmail())
                .tag(user.getDiscordInfo().getUsername() + "#" + user.getDiscordInfo().getDiscriminator())
                .nickname(Optional.ofNullable(user.getDiscordInfo().getNickname()))
                .avatarUrl(Optional.ofNullable(user.getDiscordInfo().getAvatarHash())
                        .map(avatarHash -> "https://cdn.discordapp.com/avatars/" + user.getDiscordInfo().getDiscordId() + "/" + avatarHash))
                .bannerUrl(Optional.ofNullable(user.getDiscordInfo().getBannerHash())
                        .map(bannerHash -> "https://cdn.discordapp.com/banners/" + user.getDiscordInfo().getDiscordId() + "/" + bannerHash))
                .accentColor(user.getDiscordInfo().getAccentColor())
                .roles(userRolesService.buildModel(user))
                .tags(userTagsService.buildModel(user))
                .build();
    }

    public List<UserModel> buildSummaryModels(Collection<User> users) {
        return users.stream()
                .map(this::buildSummaryModel)
                .collect(Collectors.toList());
    }

    public UserModel buildSummaryModel(User user) {
        return UserModel.builder()
                .discordId(String.valueOf(user.getDiscordInfo().getDiscordId()))
                .tag(user.getDiscordInfo().getUsername() + "#" + user.getDiscordInfo().getDiscriminator())
                .nickname(Optional.ofNullable(user.getDiscordInfo().getNickname()))
                .avatarUrl(Optional.ofNullable(user.getDiscordInfo().getAvatarHash())
                        .map(avatarHash -> "https://cdn.discordapp.com/avatars/" + user.getDiscordInfo().getDiscordId() + "/" + avatarHash))
                .build();
    }

    public List<UserModel> buildPublicModels(Collection<User> users) {
        return users.stream()
                .map(this::buildPublicModel)
                .collect(Collectors.toList());
    }

    public UserModel buildPublicModel(User user) {
        return UserModel.builder()
                .discordId(String.valueOf(user.getDiscordInfo().getDiscordId()))
                .tag(user.getDiscordInfo().getUsername() + "#" + user.getDiscordInfo().getDiscriminator())
                .nickname(Optional.ofNullable(user.getDiscordInfo().getNickname()))
                .avatarUrl(Optional.ofNullable(user.getDiscordInfo().getAvatarHash())
                        .map(avatarHash -> "https://cdn.discordapp.com/avatars/" + user.getDiscordInfo().getDiscordId() + "/" + avatarHash))
                .bannerUrl(Optional.ofNullable(user.getDiscordInfo().getBannerHash())
                        .map(bannerHash -> "https://cdn.discordapp.com/banners/" + user.getDiscordInfo().getDiscordId() + "/" + bannerHash))
                .accentColor(user.getDiscordInfo().getAccentColor())
                .roles(userRolesService.buildModel(user))
                .tags(userTagsService.buildModel(user))
                .build();
    }
}
