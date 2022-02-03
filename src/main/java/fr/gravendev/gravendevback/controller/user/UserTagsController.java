package fr.gravendev.gravendevback.controller.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.UserTagEntry;
import fr.gravendev.gravendevback.entity.user.UserTags;
import fr.gravendev.gravendevback.model.user.UserTagModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import fr.gravendev.gravendevback.service.user.UserService;
import fr.gravendev.gravendevback.service.user.UserTagEntryService;
import fr.gravendev.gravendevback.service.user.UserTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/users/{discordId}/tags")
@CrossOrigin
public class UserTagsController {

    private final AuthTokenService authTokenService;
    private final UserService userService;
    private final UserTagsService userTagsService;
    private final UserTagEntryService userTagEntryService;

    @Autowired
    public UserTagsController(AuthTokenService authTokenService,
                              UserService userService,
                              UserTagsService userTagsService,
                              UserTagEntryService userTagEntryService) {
        this.authTokenService = authTokenService;
        this.userService = userService;
        this.userTagsService = userTagsService;
        this.userTagEntryService = userTagEntryService;
    }

    @GetMapping
    public List<UserTagModel> getUserTags(@PathVariable("discordId") Long discordId) {
        User user = userService.getUser(discordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return userTagsService.buildModel(userTagsService.getTags(user));
    }

    @PutMapping
    public List<UserTagModel> putUserTags(@RequestHeader("Authorization") String authTokenStr,
                                    @PathVariable("discordId") Long discordId,
                                    @RequestBody List<UserTagModel> tagModels) {
        User user = authTokenService.checkAuthentication(authTokenStr, discordId);

        UserTags userTags = userTagsService.setTags(user, tagModels);

        return userTagsService.buildModel(userTags);
    }

    @PutMapping("/{tagId}")
    public void putUserTag(@RequestHeader("Authorization") String authTokenStr,
                           @PathVariable("discordId") Long discordId,
                           @PathVariable("tagId") Long tagId) {
        User user = authTokenService.checkAuthentication(authTokenStr, discordId);

        UserTagEntry userTagEntry = userTagEntryService.getTag(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));

        userTagsService.addTag(user, userTagEntry);
    }

    @DeleteMapping("/{tagId}")
    public void deleteUserTag(@RequestHeader("Authorization") String authTokenStr,
                              @PathVariable("discordId") Long discordId,
                              @PathVariable("tagId") Long tagId) {
        User user = authTokenService.checkAuthentication(authTokenStr, discordId);

        UserTagEntry userTagEntry = userTagEntryService.getTag(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));

        userTagsService.removeTag(user, userTagEntry);
    }
}
