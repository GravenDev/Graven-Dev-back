package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.UserDiscordInfo;
import fr.gravendev.gravendevback.model.discord.DiscordGuildMemberResponse;
import fr.gravendev.gravendevback.model.discord.DiscordUserResponse;
import fr.gravendev.gravendevback.repository.user.UserDiscordInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDiscordInfoService {

    public static final String DEFAULT_ACCENT_COLOR = "5865f2";

    private final UserDiscordInfoRepository userDiscordInfoRepository;

    public UserDiscordInfoService(UserDiscordInfoRepository userDiscordInfoRepository) {
        this.userDiscordInfoRepository = userDiscordInfoRepository;
    }

    public UserDiscordInfo create(DiscordUserResponse discordUser, DiscordGuildMemberResponse discordGuildMember) {
        return UserDiscordInfo.builder()
                .discordId(discordUser.id())
                .email(discordUser.email())
                .username(discordUser.username())
                .discriminator(discordUser.discriminator())
                .nickname(discordGuildMember.nickname())
                .avatarHash(discordUser.avatarHash())
                .bannerHash(discordUser.bannerHash())
                .accentColor(Optional.ofNullable(discordUser.accentColor())
                        .map(Integer::parseInt)
                        .map(Integer::toHexString)
                        .orElse(DEFAULT_ACCENT_COLOR))
                .build();
    }

    public UserDiscordInfo update(User user, DiscordUserResponse discordUser, DiscordGuildMemberResponse discordGuildMember) {
        UserDiscordInfo userDiscordInfo = user.getDiscordInfo();

        userDiscordInfo.setEmail(discordUser.email());
        userDiscordInfo.setUsername(discordUser.username());
        userDiscordInfo.setDiscriminator(discordUser.discriminator());
        userDiscordInfo.setNickname(discordGuildMember.nickname());
        userDiscordInfo.setAvatarHash(discordUser.avatarHash());
        userDiscordInfo.setBannerHash(discordUser.bannerHash());
        userDiscordInfo.setAccentColor(Optional.ofNullable(discordUser.accentColor())
                .map(Integer::parseInt)
                .map(Integer::toHexString)
                .orElse(DEFAULT_ACCENT_COLOR));

        return userDiscordInfoRepository.save(userDiscordInfo);
    }
}
