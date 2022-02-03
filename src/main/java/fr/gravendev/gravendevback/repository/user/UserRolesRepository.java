package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    boolean existsByUserIdAndUserRoleCodeIn(Long userId, List<String> roles);
}
