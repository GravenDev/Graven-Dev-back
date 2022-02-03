package fr.gravendev.gravendevback.repository;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findByAuthToken(String authToken);

    Optional<AuthToken> findByAuthTokenAndValidTrue(String authToken);

    boolean existsByAuthTokenAndValidTrueAndUserIsNotNull(String authToken);

    boolean existsByAuthToken(String authToken);
}
