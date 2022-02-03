package fr.gravendev.gravendevback.model.discord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscordTokensResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") long expiresIn,
        @JsonProperty("scope") String scope
) {
}
