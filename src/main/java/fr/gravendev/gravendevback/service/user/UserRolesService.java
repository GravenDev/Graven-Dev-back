package fr.gravendev.gravendevback.service.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.UserRoleEntry;
import fr.gravendev.gravendevback.entity.user.UserRoles;
import fr.gravendev.gravendevback.model.user.UserRoleModel;
import fr.gravendev.gravendevback.repository.user.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
        return userRolesRepository.existsByUserIdAndUserRoleCodeIn(user.getId(), Arrays.asList(roles));
    }

    public UserRoles create(Long[] roleIds) {
        return UserRoles.builder()
                .userRole(userRoleEntryService.parseRoles(roleIds))
                .build();
    }

    public UserRoles update(User user, Long[] roleIds) {
        UserRoles userRoles = user.getUserRoles();

        userRoles.setUserRole(userRoleEntryService.parseRoles(roleIds));

        return userRolesRepository.save(userRoles);
    }

    public List<UserRoleModel> buildModel(User user) {
        return buildModel(user.getUserRoles().getUserRole());
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
