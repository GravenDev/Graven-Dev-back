package fr.gravendev.gravendevback.service;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.model.discord.DiscordTokensResponse;
import fr.gravendev.gravendevback.model.discord.DiscordUserResponse;
import fr.gravendev.gravendevback.model.login.LoginModel;
import fr.gravendev.gravendevback.model.login.LoginStatusModel;
import fr.gravendev.gravendevback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final AuthTokenService authTokenService;
    private final UserService userService;
    private final DiscordService discordService;

    @Autowired
    public LoginService(AuthTokenService authTokenService,
                        UserService userService,
                        DiscordService discordService) {
        this.authTokenService = authTokenService;
        this.userService = userService;
        this.discordService = discordService;
    }

    public User login(LoginModel loginModel, AuthToken authToken) {

        if (authTokenService.isLoggedIn(authToken)) return authToken.getUser();

        DiscordTokensResponse discordTokens = discordService.getTokensFromCode(loginModel.getCode());
        DiscordUserResponse discordUser = discordService.retrieveUser(discordTokens.tokenType(), discordTokens.accessToken());

        Optional<User> user = userService.getUser(discordUser.id());

        return user
                .map(value -> userService.update(value, authToken, discordTokens, discordUser))
                .orElseGet(() -> userService.create(authToken, discordTokens, discordUser));
    }

    public LoginStatusModel buildStatusModel(String authTokenStr) {
        boolean isLoggedIn = authTokenService.isLoggedIn(authTokenStr);

        return LoginStatusModel.builder()
                .loggedIn(isLoggedIn)
                .user(userService.getUser(authTokenStr)
                        .filter(user -> isLoggedIn)
                        .map(userService::buildPrivateModel)
                        .orElse(null))
                .build();
    }
}
