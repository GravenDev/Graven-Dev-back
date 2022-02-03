package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.entity.user.UserTagEntry;
import fr.gravendev.gravendevback.entity.user.UserTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagsRepository extends JpaRepository<UserTags, Long> {

    UserTags findByUser(User user);

    void deleteAllByUserTagEntries(UserTagEntry userTagEntry);
}
