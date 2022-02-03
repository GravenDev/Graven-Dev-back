package fr.gravendev.gravendevback.controller.user;

import fr.gravendev.gravendevback.entity.user.UserTagEntry;
import fr.gravendev.gravendevback.model.user.UserTagModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import fr.gravendev.gravendevback.service.user.UserTagEntryService;
import fr.gravendev.gravendevback.service.user.UserTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin
public class UserTagEntryController {

    private final AuthTokenService authTokenService;
    private final UserTagEntryService userTagEntryService;
    private final UserTagsService userTagsService;

    @Autowired
    public UserTagEntryController(AuthTokenService authTokenService,
                                  UserTagEntryService userTagEntryService,
                                  UserTagsService userTagsService) {
        this.authTokenService = authTokenService;
        this.userTagEntryService = userTagEntryService;
        this.userTagsService = userTagsService;
    }

    @GetMapping("/tags")
    public List<UserTagModel> getTags() {
        return userTagEntryService.buildModel(userTagEntryService.getTags());
    }

    @PostMapping("/tags")
    public UserTagModel postTag(@RequestHeader("Authorization") String authTokenStr,
                                @RequestBody UserTagModel userTagModel) {
        authTokenService.checkRole(authTokenStr, "admin", "modo");

        UserTagEntry userTagEntry = userTagEntryService.create(userTagModel);

        return userTagEntryService.buildModel(userTagEntry);
    }

    @GetMapping("/tags/{tagId}")
    public Optional<UserTagModel> getTag(@PathVariable("tagId") Long tagId) {
        return userTagEntryService.getTag(tagId)
                .map(userTagEntryService::buildModel);
    }

    @PutMapping("/tags/{tagId}")
    public UserTagModel putTag(@RequestHeader("Authorization") String authTokenStr,
                               @PathVariable("tagId") Long tagId,
                               @RequestBody UserTagModel userTagModel) {
        authTokenService.checkRole(authTokenStr, "admin", "modo");

        UserTagEntry userTagEntry = userTagEntryService.getTag(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));

        userTagEntry = userTagEntryService.update(userTagEntry, userTagModel);

        return userTagEntryService.buildModel(userTagEntry);
    }

    @DeleteMapping("/tags/{tagId}")
    public void deleteTag(@RequestHeader("Authorization") String authTokenStr,
                          @PathVariable("tagId") Long tagId) {
        authTokenService.checkRole(authTokenStr, "admin", "modo");

        UserTagEntry userTagEntry = userTagEntryService.getTag(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));

        userTagsService.removeAllTags(userTagEntry);
        userTagEntryService.remove(userTagEntry);
    }
}
