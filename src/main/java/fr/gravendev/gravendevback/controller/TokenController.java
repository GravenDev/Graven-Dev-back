package fr.gravendev.gravendevback.controller;

import fr.gravendev.gravendevback.model.authtoken.AuthTokenModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tokens")
@CrossOrigin
public class TokenController {

    private final AuthTokenService authTokenService;

    @Autowired
    public TokenController(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @GetMapping("/create")
    public AuthTokenModel createAuthToken() {
        return authTokenService.buildModel(authTokenService.createAuthToken());
    }

    @GetMapping("/validate")
    public AuthTokenModel validateAuthToken(@RequestParam("token") String authTokenStr) {
        return authTokenService.buildModel(authTokenService.validateAuthToken(authTokenStr));
    }
}
