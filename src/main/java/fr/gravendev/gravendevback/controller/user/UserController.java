package fr.gravendev.gravendevback.controller.user;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.model.user.UserModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import fr.gravendev.gravendevback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin
public class UserController {

    private final AuthTokenService authTokenService;
    private final UserService userService;

    @Autowired
    public UserController(AuthTokenService authTokenService, UserService userService) {
        this.authTokenService = authTokenService;
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> users() {
        return userService.buildSummaryModels(userService.getUsers());
    }

    @GetMapping("/me")
    public UserModel userInfo(@RequestHeader("Authorization") String authTokenStr) {
        AuthToken authToken = authTokenService.checkTokenValidity(authTokenStr);

        if (authToken.getUser() == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");

        return userService.buildPrivateModel(authToken.getUser());
    }

    @GetMapping("/{discordId}")
    public UserModel userInfo(@PathVariable("discordId") Long discordId) {
        return userService.getUser(discordId)
                .map(userService::buildPublicModel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{discordId}/summary")
    public UserModel userSummary(@PathVariable("discordId") Long discordId) {
        return userService.getUser(discordId)
                .map(userService::buildSummaryModel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
