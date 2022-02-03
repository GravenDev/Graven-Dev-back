package fr.gravendev.gravendevback.model.discord;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record DiscordGuildMemberResponse (
        @JsonProperty("user") DiscordUserResponse discordUser,
        @JsonProperty("nick") String nickname,
        @JsonProperty("avatarHash") String avatar,
        @JsonProperty("roles") Long[] roles,
        @JsonProperty("joined_at") Instant joinedAt,
        @JsonProperty("permissions") String permissions
) {}
