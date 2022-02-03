package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuthTokensContaining(AuthToken authToken);

    Optional<User> findByDiscordInfoDiscordId(Long discordId);
}
