package fr.gravendev.gravendevback.repository.user.about;

import fr.gravendev.gravendevback.entity.user.about.AboutIntro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutIntroRepository extends JpaRepository<AboutIntro, Long> {
}
