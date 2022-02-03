package fr.gravendev.gravendevback.controller.user.about;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.model.user.about.AboutIntroModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import fr.gravendev.gravendevback.service.user.UserService;
import fr.gravendev.gravendevback.service.user.about.AboutIntroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/v1/users/{discordId}/about/intro")
@CrossOrigin
public class AboutIntroController {

    private final AuthTokenService authTokenService;
    private final UserService userService;
    private final AboutIntroService aboutIntroService;

    @Autowired
    public AboutIntroController(AuthTokenService authTokenService,
                                UserService userService,
                                AboutIntroService aboutIntroService) {
        this.authTokenService = authTokenService;
        this.userService = userService;
        this.aboutIntroService = aboutIntroService;
    }

    @GetMapping
    public AboutIntroModel getIntro(@PathVariable("discordId") Long discordId) {
        User user = userService.getUser(discordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return aboutIntroService.buildModel(user);
    }

    @PutMapping
    public void putIntro(@RequestHeader("Authorization") String authTokenStr,
                         @PathVariable("discordId") Long discordId,
                         @RequestBody AboutIntroModel aboutIntroModel) {
        User user = authTokenService.checkAuthentication(authTokenStr, discordId);

        String text = Optional.ofNullable(aboutIntroModel)
                .map(AboutIntroModel::getText)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing text"));

        aboutIntroService.setIntro(user, text);
    }
}
