package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.role.UserRoleEntry;
import fr.gravendev.gravendevback.entity.user.role.UserRoles;
import fr.gravendev.gravendevback.entity.user.role.UserRolesKey;
import fr.gravendev.gravendevback.model.user.UserRoleModel;
import fr.gravendev.gravendevback.repository.user.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRolesService {

    private final UserRolesRepository userRolesRepository;
    private final UserRoleEntryService userRoleEntryService;

    @Autowired
    public UserRolesService(UserRolesRepository userRolesRepository,
                            UserRoleEntryService userRoleEntryService) {
        this.userRolesRepository = userRolesRepository;
        this.userRoleEntryService = userRoleEntryService;
    }

    public boolean hasRole(User user, String... roles) {
        return userRolesRepository.existsByUserIdAndUserRoleEntryCodeIn(user.getId(), Arrays.asList(roles));
    }

    public List<UserRoles> update(User user, Long[] roleIds) {
        Set<UserRoles> userRoles = userRoleEntryService.parseRoles(roleIds).stream()
                .map(userRoleEntry -> UserRoles.builder()
                        .id(UserRolesKey.builder()
                                .userId(user.getId())
                                .userRoleId(userRoleEntry.getId())
                                .build())
                        .user(user)
                        .userRoleEntry(userRoleEntry)
                        .build())
                .collect(Collectors.toSet());

        return userRolesRepository.saveAll(userRoles);
    }

    public List<UserRoleModel> buildModel(User user) {
        return buildModel(user.getUserRoles().stream()
                .map(UserRoles::getUserRoleEntry)
                .collect(Collectors.toSet()));
    }

    public List<UserRoleModel> buildModel(Set<UserRoleEntry> userRoleEntries) {
        return userRoleEntries.stream()
                .sorted(Comparator.comparing(UserRoleEntry::getPosition))
                .map(userRole -> UserRoleModel.builder()
                        .name(userRole.getName())
                        .color(userRole.getColor())
                        .icon(userRole.getIcon())
                        .build())
                .toList();
    }
}
