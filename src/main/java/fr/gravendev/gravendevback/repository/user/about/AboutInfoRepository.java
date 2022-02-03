package fr.gravendev.gravendevback.repository.user.about;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.about.AboutInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutInfoRepository extends JpaRepository<AboutInfo, Long> {

    Optional<AboutInfo> findByUserAndAboutInfoEntryId(User user, Long aboutInfoEntryId);
}
