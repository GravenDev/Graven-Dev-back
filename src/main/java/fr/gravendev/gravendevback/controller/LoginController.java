package fr.gravendev.gravendevback.controller;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.model.login.LoginModel;
import fr.gravendev.gravendevback.model.login.LoginStatusModel;
import fr.gravendev.gravendevback.model.user.UserModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import fr.gravendev.gravendevback.service.LoginService;
import fr.gravendev.gravendevback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/login")
@CrossOrigin
public class LoginController {

    private final AuthTokenService authTokenService;
    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    public LoginController(AuthTokenService authTokenService,
                           UserService userService,
                           LoginService loginService) {
        this.authTokenService = authTokenService;
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping
    public UserModel login(@RequestHeader("Authorization") String authTokenStr,
                           @RequestBody LoginModel loginModel) {
        AuthToken authToken = authTokenService.checkTokenValidity(authTokenStr);

        if (loginModel.getCode().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing code");

        User user = loginService.login(loginModel, authToken);

        return userService.buildPrivateModel(user);
    }

    @GetMapping("/status")
    public LoginStatusModel loginStatus(@RequestHeader("Authorization") String authTokenStr) {

        if (authTokenStr == null || authTokenStr.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing token");

        if (!authTokenService.exists(authTokenStr)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");

        return loginService.buildStatusModel(authTokenStr);
    }
}
