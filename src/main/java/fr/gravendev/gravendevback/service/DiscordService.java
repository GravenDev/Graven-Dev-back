package fr.gravendev.gravendevback.service;

import fr.gravendev.gravendevback.entity.DiscordToken;
import fr.gravendev.gravendevback.model.discord.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class DiscordService {

    private static final Duration TIMEOUT = Duration.ofSeconds(3);

    private static final String REDIRECT_URI = "https://phpstorm-a-preview.ftmnet.com/login/callback";

    private final WebClient discordApiClient;

    @Value("${DISCORD_CLIENT_ID}")
    private String clientId;

    @Value("${DISCORD_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${DISCORD_BOT_TOKEN}")
    private String gravensBotToken;

    @Value("${GUILD_ID}")
    private String gravenDevGuildId;

    @Autowired
    public DiscordService(WebClient discordWebClient) {
        this.discordApiClient = discordWebClient;
    }

    public DiscordTokensResponse getTokensFromCode(String code) {
        return discordApiClient
                .post()
                .uri("/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("grant_type", DiscordGrantType.AUTHORIZATION_CODE.toString())
                        .with("code", code)
                        .with("redirect_uri", REDIRECT_URI))
                .retrieve()
                .bodyToMono(DiscordTokensResponse.class)
                .block(TIMEOUT);
    }

    public DiscordTokensResponse getRefreshedTokens(DiscordToken discordToken) {
        return discordApiClient
                .post()
                .uri("/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("grant_type", DiscordGrantType.REFRESH_TOKEN.toString())
                        .with("refresh_token", discordToken.getRefreshToken()))
                .retrieve()
                .bodyToMono(DiscordTokensResponse.class)
                .block(TIMEOUT);
    }

    public DiscordUserResponse retrieveUser(String tokenType, String accessToken) {
        return discordApiClient
                .get()
                .uri("/users/@me")
                .header("Authorization", tokenType + " " + accessToken)
                .retrieve()
                .bodyToMono(DiscordUserResponse.class)
                .block(TIMEOUT);
    }

    public DiscordGuildMemberResponse retrieveGuildMember(long discordId, String accessToken) {
        return discordApiClient
                .get()
                .uri("/users/@me/guilds/" + gravenDevGuildId + "/member")
                .header("Authorization", "Bearer " + accessToken)
                //.uri("/guilds/" + gravenDevGuildId + "/members/" + discordId)
                //.header("Authorization", "Bot " + gravensBotToken)
                .retrieve()
                .bodyToMono(DiscordGuildMemberResponse.class)
                .block(TIMEOUT);
    }

    public void joinGuild(String accessToken, long memberId) {
        discordApiClient
                .put()
                .uri("/guilds/" + gravenDevGuildId + "/members/" + memberId)
                .bodyValue(JoinGuildRequest.builder().accessToken(accessToken).build())
                .header("Authorization", "Bot " + gravensBotToken)
                .retrieve()
                .toBodilessEntity()
                .block(TIMEOUT);
    }
}
