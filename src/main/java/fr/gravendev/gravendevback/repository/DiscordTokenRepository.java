package fr.gravendev.gravendevback.repository;

import fr.gravendev.gravendevback.entity.DiscordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscordTokenRepository extends JpaRepository<DiscordToken, Long> {

    Optional<DiscordToken> findDiscordTokenByUserDiscordInfoDiscordId(Long discordId);
}
