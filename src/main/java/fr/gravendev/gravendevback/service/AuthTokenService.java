package fr.gravendev.gravendevback.service;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.model.authtoken.AuthTokenModel;
import fr.gravendev.gravendevback.repository.AuthTokenRepository;
import fr.gravendev.gravendevback.service.user.UserRolesService;
import fr.gravendev.gravendevback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final UserService userService;
    private final UserRolesService userRolesService;

    @Autowired
    public AuthTokenService(AuthTokenRepository authTokenRepository,
                            UserService userService,
                            UserRolesService userRolesService) {
        this.authTokenRepository = authTokenRepository;
        this.userService = userService;
        this.userRolesService = userRolesService;
    }

    public AuthToken createAuthToken() {
        AuthToken authToken = new AuthToken();
        while (authTokenRepository.existsByAuthToken(authToken.getAuthToken())) {
            authToken = new AuthToken();
        }
        authTokenRepository.save(authToken);
        return authToken;
    }

    public boolean exists(String authToken) {
        return authTokenRepository.existsByAuthToken(authToken);
    }

    public boolean isLoggedIn(String authToken) {
        return authTokenRepository.existsByAuthTokenAndValidTrueAndUserIsNotNull(authToken);
    }

    public boolean isLoggedIn(AuthToken authToken) {
        return authToken.isValid() && authToken.isLinked();
    }

    public AuthToken validateAuthToken(String authToken) {
        return authTokenRepository.findByAuthTokenAndValidTrue(authToken)
                .orElseGet(this::createAuthToken);
    }

    public AuthToken validateAuthToken(AuthToken authToken) {
        return authToken.isValid()
                ? authToken
                : createAuthToken();
    }

    public void revokeAuthToken(String authTokenStr) {
        authTokenRepository.findByAuthToken(authTokenStr)
                .ifPresent(this::revokeAuthToken);
    }

    public void revokeAuthToken(AuthToken authToken) {
        authToken.setValid(false);
        authTokenRepository.save(authToken);
    }

    public AuthToken checkTokenValidity(String authTokenStr) throws ResponseStatusException {
        if (authTokenStr == null || authTokenStr.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token");
        }

        Optional<AuthToken> authToken = authTokenRepository.findByAuthToken(authTokenStr);

        if (authToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        if (!authToken.get().isValid()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalidated token");
        }

        return authToken.get();
    }

    public User checkAuthentication(String authTokenStr) {
        AuthToken authToken = checkTokenValidity(authTokenStr);

        if (!authToken.isLogged())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");

        return authToken.getUser();
    }

    public User checkAuthentication(String authTokenStr, Long discordId) {
        User user = checkAuthentication(authTokenStr);

        if (!Objects.equals(user.getDiscordInfo().getDiscordId(), discordId))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token not authorized on this user");

        return user;
    }

    public User checkRole(String authTokenStr, String... roleCode) {
        User user = checkAuthentication(authTokenStr);

        if (!userService.hasRole(user, roleCode))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough permissions");

        return user;
    }

    public AuthTokenModel buildModel(AuthToken authToken) {
        return AuthTokenModel.builder()
                .authToken(authToken.getAuthToken())
                .build();
    }
}
