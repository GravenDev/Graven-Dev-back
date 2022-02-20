package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.role.UserRoleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface UserRoleEntryRepository extends JpaRepository<UserRoleEntry, Long> {

    Set<UserRoleEntry> findAllByDiscordIdIn(Collection<Long> ids);
}
