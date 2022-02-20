package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.tag.UserTagEntry;
import fr.gravendev.gravendevback.entity.user.tag.UserTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagsRepository extends JpaRepository<UserTags, Long> {

    List<UserTags> findByUser(User user);

    Optional<UserTags> findByUserAndUserTagEntry(User user, UserTagEntry userTagEntry);

    List<UserTags> findAllByUserTagEntry(UserTagEntry userTagEntry);
}
