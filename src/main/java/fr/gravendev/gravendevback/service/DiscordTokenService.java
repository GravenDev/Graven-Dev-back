package fr.gravendev.gravendevback.service;

import fr.gravendev.gravendevback.entity.DiscordToken;
import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.model.discord.DiscordTokensResponse;
import fr.gravendev.gravendevback.repository.DiscordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DiscordTokenService {

    private final DiscordTokenRepository discordTokenRepository;
    private final DiscordService discordService;

    @Autowired
    public DiscordTokenService(DiscordTokenRepository discordTokenRepository,
                               DiscordService discordService) {
        this.discordTokenRepository = discordTokenRepository;
        this.discordService = discordService;
    }

    public DiscordToken create(DiscordTokensResponse discordTokens) {
        return DiscordToken.builder()
                .accessToken(discordTokens.accessToken())
                .refreshToken(discordTokens.refreshToken())
                .expiresAt(Instant.now().plusSeconds(discordTokens.expiresIn()))
                .build();
    }

    public DiscordToken update(User user, DiscordTokensResponse discordTokens) {
        DiscordToken discordToken = user.getDiscordToken();

        discordToken.setAccessToken(discordTokens.accessToken());
        discordToken.setRefreshToken(discordTokens.refreshToken());
        discordToken.setExpiresAt(Instant.now().plusSeconds(discordTokens.expiresIn()));

        return discordTokenRepository.save(discordToken);
    }

    public void refreshTokens(DiscordToken discordToken) {
        DiscordTokensResponse discordTokensResponse = discordService.getRefreshedTokens(discordToken);

        discordToken.setAccessToken(discordTokensResponse.accessToken());
        discordToken.setRefreshToken(discordTokensResponse.refreshToken());
        discordToken.setExpiresAt(Instant.now().plusSeconds(discordTokensResponse.expiresIn()));

        discordTokenRepository.save(discordToken);
    }
}
