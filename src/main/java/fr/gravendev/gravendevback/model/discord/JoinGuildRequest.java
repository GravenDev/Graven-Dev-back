package fr.gravendev.gravendevback.model.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
public @Data class JoinGuildRequest {

    @JsonProperty("access_token")
    private final String accessToken;
}
