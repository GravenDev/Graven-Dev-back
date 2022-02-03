package fr.gravendev.gravendevback.repository.user;

import fr.gravendev.gravendevback.entity.user.UserDiscordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDiscordInfoRepository extends JpaRepository<UserDiscordInfo, Long> {

    Optional<UserDiscordInfo> findByDiscordId(Long discordId);
}
