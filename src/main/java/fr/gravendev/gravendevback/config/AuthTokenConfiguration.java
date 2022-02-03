package fr.gravendev.gravendevback.config;

import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthTokenConfiguration {

    @Value("${DEV_AUTHTOKEN}")
    private String devAuthToken;

    @Bean
    public CommandLineRunner persistentToken(AuthTokenRepository authTokenRepository) {
        return args -> populateAuthToken(authTokenRepository);
    }

    public void populateAuthToken(AuthTokenRepository authTokenRepository) {
        if (!authTokenRepository.findAll().isEmpty()) return;

        authTokenRepository.save(new AuthToken(devAuthToken));
    }
}
