package fr.gravendev.gravendevback.controller;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.model.authtoken.AuthTokenModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/logout")
@CrossOrigin
public class LogoutController {

    private final AuthTokenService authTokenService;

    @Autowired
    public LogoutController(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @PostMapping
    public AuthTokenModel logout(@RequestHeader("Authorization") String authTokenStr) {
        AuthToken authToken = authTokenService.checkTokenValidity(authTokenStr);

        if(!authToken.isLinked()) return authTokenService.buildModel(authToken);

        authTokenService.revokeAuthToken(authTokenStr);

        return authTokenService.buildModel(authTokenService.createAuthToken());
    }
}
