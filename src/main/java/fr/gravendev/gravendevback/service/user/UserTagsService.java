package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.tag.UserTagEntry;
import fr.gravendev.gravendevback.entity.user.tag.UserTags;
import fr.gravendev.gravendevback.entity.user.tag.UserTagsKey;
import fr.gravendev.gravendevback.model.user.UserTagModel;
import fr.gravendev.gravendevback.repository.user.UserTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<UserTags> getTags(User user) {
        return userTagsRepository.findByUser(user);
    }

    public UserTags addTag(User user, UserTagEntry userTagEntry) {
        UserTags userTags = UserTags.builder()
                .id(UserTagsKey.builder()
                        .userId(user.getId())
                        .userTagId(userTagEntry.getId())
                        .build())
                .user(user)
                .userTagEntry(userTagEntry)
                .build();

        return userTagsRepository.save(userTags);
    }

    public void removeTag(User user, UserTagEntry userTagEntry) {
        Optional<UserTags> userTags = userTagsRepository.findByUserAndUserTagEntry(user, userTagEntry);

        userTags.ifPresent(userTagsRepository::delete);
    }

    public void removeAllTags(UserTagEntry userTagEntry) {
        List<UserTags> userTags = userTagsRepository.findAllByUserTagEntry(userTagEntry);

        userTagsRepository.deleteAll(userTags);
    }

    public List<UserTagModel> buildModel(User user) {
        return userTagEntryService.buildModel(user.getUserTags().stream()
                .map(UserTags::getUserTagEntry)
                .collect(Collectors.toSet()));
    }
}
