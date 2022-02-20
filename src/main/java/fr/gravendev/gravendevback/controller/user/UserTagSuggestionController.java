package fr.gravendev.gravendevback.controller.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.tag.UserTagSuggestion;
import fr.gravendev.gravendevback.model.user.UserTagModel;
import fr.gravendev.gravendevback.service.AuthTokenService;
import fr.gravendev.gravendevback.service.user.UserTagSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin
public class UserTagSuggestionController {

    private final AuthTokenService authTokenService;
    private final UserTagSuggestionService userTagSuggestionService;

    @Autowired
    public UserTagSuggestionController(AuthTokenService authTokenService,
                                       UserTagSuggestionService userTagSuggestionService) {
        this.authTokenService = authTokenService;
        this.userTagSuggestionService = userTagSuggestionService;
    }

    @GetMapping("/tags/suggestions")
    public List<UserTagModel> getTagsSuggestions(@RequestHeader("Authorization") String authTokenStr) {
        authTokenService.checkRole(authTokenStr, "admin", "modo");

        return userTagSuggestionService.buildModel(userTagSuggestionService.getTagsSuggestions());
    }

    @GetMapping("/tags/suggestions/count")
    public Long getTagsSuggestionsCount(@RequestHeader("Authorization") String authTokenStr) {
        authTokenService.checkRole(authTokenStr, "admin", "modo");

        return userTagSuggestionService.getCount();
    }

    @PostMapping("/tags/suggestions")
    public void postTagsSuggestions(@RequestHeader("Authorization") String authTokenStr,
                                    @RequestBody UserTagModel userTagModel) {
        User user = authTokenService.checkAuthentication(authTokenStr);

        userTagSuggestionService.create(user, userTagModel);
    }

    @GetMapping("/tags/suggestions/{tagId}")
    public UserTagSuggestion getTagSuggestion(@RequestHeader("Authorization") String authTokenStr,
                                              @PathVariable("tagId") Long tagId) {
        authTokenService.checkRole(authTokenStr, "admin", "modo");

        return userTagSuggestionService.get(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag suggestion not found"));
    }

    @PutMapping("/tags/suggestions/{tagId}")
    public void putTagsSuggestions(@RequestHeader("Authorization") String authTokenStr,
                                   @PathVariable("tagId") Long tagId,
                                   @RequestBody UserTagModel userTagModel) {
        authTokenService.checkAuthentication(authTokenStr);

        authTokenService.checkRole(authTokenStr, "admin", "modo");

        UserTagSuggestion userTagSuggestion = userTagSuggestionService.get(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag suggestion not found"));

        userTagSuggestionService.update(userTagSuggestion, userTagModel);
    }

    @DeleteMapping("/tags/suggestions/{tagId}")
    public void deleteTagsSuggestions(@RequestHeader("Authorization") String authTokenStr,
                                      @PathVariable("tagId") Long tagId) {
        authTokenService.checkAuthentication(authTokenStr);

        authTokenService.checkRole(authTokenStr, "admin", "modo");

        UserTagSuggestion userTagSuggestion = userTagSuggestionService.get(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag suggestion not found"));

        userTagSuggestionService.delete(userTagSuggestion);
    }
}
