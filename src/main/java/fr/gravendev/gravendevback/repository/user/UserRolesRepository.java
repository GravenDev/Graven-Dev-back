package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.role.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    boolean existsByUserIdAndUserRoleEntryCodeIn(Long userId, List<String> roles);
}
