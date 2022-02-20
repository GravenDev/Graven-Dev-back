package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.tag.UserTagSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagSuggestionRepository extends JpaRepository<UserTagSuggestion, Long> {
}
