package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.tag.UserTagEntry;
import fr.gravendev.gravendevback.model.user.UserTagModel;
import fr.gravendev.gravendevback.repository.user.UserTagEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserTagEntryService {

    private final UserTagEntryRepository userTagEntryRepository;

    @Autowired
    public UserTagEntryService(UserTagEntryRepository userTagEntryRepository) {
        this.userTagEntryRepository = userTagEntryRepository;
    }

    public List<UserTagEntry> getTags() {
        return userTagEntryRepository.findAll();
    }

    public Optional<UserTagEntry> getTag(Long id) {
        return getTagById(id);
    }

    public Optional<UserTagEntry> getTagById(Long id) {
        return userTagEntryRepository.findById(id);
    }

    public UserTagEntry create(UserTagModel userTagModel) {

        if (userTagModel.getPosition() == 0) {
            userTagModel.setPosition(userTagEntryRepository.findTopByOrderByPositionDesc()
                    .map(UserTagEntry::getPosition)
                    .map(position -> position + 1)
                    .orElse(1));
        }

        UserTagEntry userTagEntry = UserTagEntry.builder()
                .name(userTagModel.getName())
                .color(userTagModel.getColor())
                .icon(userTagModel.getIcon())
                .position(userTagModel.getPosition())
                .build();

        userTagEntryRepository.save(userTagEntry);

        return userTagEntry;
    }

    public UserTagEntry update(UserTagEntry userTagEntry, UserTagModel userTagModel) {
        userTagEntry.setName(userTagModel.getName());
        userTagEntry.setColor(userTagModel.getColor());
        userTagEntry.setIcon(userTagModel.getIcon());
        userTagEntry.setPosition(userTagModel.getPosition());

        userTagEntryRepository.save(userTagEntry);

        return userTagEntry;
    }

    public void remove(UserTagEntry userTagEntry) {
        userTagEntryRepository.delete(userTagEntry);
    }

    public List<UserTagModel> buildModel(Collection<UserTagEntry> userTagEntries) {
        return userTagEntries.stream()
                .distinct()
                .sorted(Comparator.comparing(UserTagEntry::getPosition))
                .map(this::buildModel)
                .collect(java.util.stream.Collectors.toList());
    }

    public UserTagModel buildModel(UserTagEntry userTagEntry) {
        return UserTagModel.builder()
                .id(userTagEntry.getId())
                .name(userTagEntry.getName())
                .color(userTagEntry.getColor())
                .icon(userTagEntry.getIcon())
                .build();
    }
}
