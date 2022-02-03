package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.UserTagSuggestion;
import fr.gravendev.gravendevback.model.user.UserTagModel;
import fr.gravendev.gravendevback.repository.user.UserTagSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserTagSuggestionService {

    private final UserTagSuggestionRepository userTagSuggestionRepository;
    private final UserService userService;

    @Autowired
    public UserTagSuggestionService(UserTagSuggestionRepository userTagSuggestionRepository,
                                    UserService userService) {
        this.userTagSuggestionRepository = userTagSuggestionRepository;
        this.userService = userService;
    }

    public List<UserTagSuggestion> getTagsSuggestions() {
        return userTagSuggestionRepository.findAll();
    }

    public Long getCount() {
        return userTagSuggestionRepository.count();
    }

    public Optional<UserTagSuggestion> get(Long id) {
        return getById(id);
    }

    public Optional<UserTagSuggestion> getById(Long id) {
        return userTagSuggestionRepository.findById(id);
    }

    public UserTagSuggestion create(User user, UserTagModel userTagModel) {
        UserTagSuggestion userTagSuggestion = UserTagSuggestion.builder()
                .suggestedBy(user)
                .name(userTagModel.getName())
                .icon(userTagModel.getIcon())
                .build();

        userTagSuggestionRepository.save(userTagSuggestion);

        return userTagSuggestion;
    }

    public UserTagSuggestion update(UserTagSuggestion userTagSuggestion, UserTagModel userTagModel) {
        userTagSuggestion.setName(userTagModel.getName());
        userTagSuggestion.setIcon(userTagModel.getIcon());

        userTagSuggestionRepository.save(userTagSuggestion);

        return userTagSuggestion;
    }

    public void delete(UserTagSuggestion userTagSuggestion) {
        userTagSuggestionRepository.delete(userTagSuggestion);
    }

    public UserTagSuggestion extractTag(User user, UserTagModel userTagModel) {
        return UserTagSuggestion.builder()
                .suggestedBy(user)
                .name(userTagModel.getName())
                .icon(userTagModel.getIcon())
                .build();
    }

    public List<UserTagModel> buildModel(List<UserTagSuggestion> userTagSuggestions) {
        return userTagSuggestions.stream()
                .sorted(Comparator.comparing(UserTagSuggestion::getId))
                .map(this::buildModel)
                .toList();
    }

    public UserTagModel buildModel(UserTagSuggestion userTagSuggestion) {
        return UserTagModel.builder()
                .id(userTagSuggestion.getId())
                .name(userTagSuggestion.getName())
                .icon(userTagSuggestion.getIcon())
                .user(userService.buildSummaryModel(userTagSuggestion.getSuggestedBy()))
                .build();
    }
}
