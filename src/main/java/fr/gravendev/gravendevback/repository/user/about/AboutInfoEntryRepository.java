package fr.gravendev.gravendevback.repository.user.about;

import fr.gravendev.gravendevback.entity.user.about.AboutInfoEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutInfoEntryRepository extends JpaRepository<AboutInfoEntry, Long> {
}
