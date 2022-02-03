package fr.gravendev.gravendevback.controller.user.about;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.model.user.about.AboutInfoModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import fr.gravendev.gravendevback.service.user.UserService;
import fr.gravendev.gravendevback.service.user.about.AboutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/users/{discordId}/about/info")
@CrossOrigin
public class AboutInfoController {

    private final AuthTokenService authTokenService;
    private final UserService userService;
    private final AboutInfoService aboutInfoService;

    @Autowired
    public AboutInfoController(AuthTokenService authTokenService,
                               UserService userService,
                               AboutInfoService aboutInfoService) {
        this.authTokenService = authTokenService;
        this.userService = userService;
        this.aboutInfoService = aboutInfoService;
    }

    @GetMapping
    public List<AboutInfoModel> getInfo(@PathVariable("discordId") Long discordId) {
        return userService.getUser(discordId)
                .map(aboutInfoService::buildModel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping
    public void postInfo(@RequestHeader("Authorization") String authTokenStr,
                         @PathVariable("discordId") Long discordId,
                         @RequestBody AboutInfoModel aboutInfoModel) {
        User user = authTokenService.checkAuthentication(authTokenStr, discordId);

        aboutInfoService.set(user, aboutInfoModel);
    }

    @GetMapping("/{aboutInfoId}")
    public AboutInfoModel getInfo(@PathVariable("discordId") Long discordId,
                                  @PathVariable("aboutInfoId") Long aboutInfoId) {
        User user = userService.getUser(discordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return aboutInfoService.get(user, aboutInfoId)
                .map(aboutInfoService::buildModel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "About info not found"));
    }

    @PutMapping("/{aboutInfoId}")
    public void putInfo(@RequestHeader("Authorization") String authTokenStr,
                        @PathVariable("discordId") Long discordId,
                        @PathVariable("aboutInfoId") Long aboutInfoId,
                        @RequestBody AboutInfoModel aboutInfoModel) {
        User user = authTokenService.checkAuthentication(authTokenStr, discordId);

        aboutInfoModel.setId(aboutInfoId);
        aboutInfoService.set(user, aboutInfoModel);
    }

    @DeleteMapping("/{aboutInfoId}")
    public void deleteInfo(@RequestHeader("Authorization") String authTokenStr,
                           @PathVariable("discordId") Long discordId,
                           @PathVariable("aboutInfoId") Long aboutInfoId) {
        User user = authTokenService.checkAuthentication(authTokenStr, discordId);

        aboutInfoService.remove(user, aboutInfoId);
    }
}
