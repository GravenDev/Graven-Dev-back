package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.UserTagEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTagEntryRepository extends JpaRepository<UserTagEntry, Long> {

    Optional<UserTagEntry> findTopByOrderByPositionDesc();
}
