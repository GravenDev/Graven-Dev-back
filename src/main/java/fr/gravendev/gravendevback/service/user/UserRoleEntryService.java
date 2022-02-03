package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.UserRoleEntry;
import fr.gravendev.gravendevback.repository.user.UserRoleEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserRoleEntryService {

    private final UserRoleEntryRepository userRoleEntryRepository;

    @Autowired
    public UserRoleEntryService(UserRoleEntryRepository userRoleEntryRepository) {
        this.userRoleEntryRepository = userRoleEntryRepository;
    }

    public Set<UserRoleEntry> parseRoles(Long[] snowflakes) {
        if (snowflakes == null || snowflakes.length == 0) return new HashSet<>();
        return userRoleEntryRepository.findAllByDiscordIdIn(List.of(snowflakes));
    }
}
