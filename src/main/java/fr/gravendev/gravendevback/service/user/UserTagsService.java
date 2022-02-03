package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.UserTagEntry;
import fr.gravendev.gravendevback.entity.user.UserTags;
import fr.gravendev.gravendevback.model.user.UserTagModel;
import fr.gravendev.gravendevback.repository.user.UserTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserTagsService {

    private final UserTagsRepository userTagsRepository;
    private final UserTagEntryService userTagEntryService;

    @Autowired
    public UserTagsService(UserTagsRepository userTagsRepository,
                           UserTagEntryService userTagEntryService) {
        this.userTagsRepository = userTagsRepository;
        this.userTagEntryService = userTagEntryService;
    }

    public UserTags getTags(User user) {
        return userTagsRepository.findByUser(user);
    }

    public UserTags setTags(User user, List<UserTagModel> userTagModels) {
        Set<UserTagEntry> userTags = userTagModels.stream()
                .map(UserTagModel::getId)
                .map(userTagEntryService::getTag)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        user.getUserTags().setUserTagEntries(userTags);

        return userTagsRepository.save(user.getUserTags());
    }

    public void addTag(User user, UserTagEntry userTagEntry) {
        UserTags userTags = user.getUserTags();

        userTags.getUserTagEntries().add(userTagEntry);

        userTagsRepository.save(userTags);
    }

    public void removeTag(User user, UserTagEntry userTagEntry) {
        UserTags userTags = user.getUserTags();

        user.getUserTags().getUserTagEntries().remove(userTagEntry);

        userTagsRepository.save(userTags);
    }

    public void removeAllTags(UserTagEntry userTagEntry) {
        userTagsRepository.deleteAllByUserTagEntries(userTagEntry);
    }

    public UserTags create() {
        return UserTags.builder()
                .userTagEntries(Set.of())
                .build();
    }

    public List<UserTagModel> buildModel(UserTags userTags) {
        return userTagEntryService.buildModel(userTags.getUserTagEntries());
    }
}
