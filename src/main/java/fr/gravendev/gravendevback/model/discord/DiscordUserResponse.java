package fr.gravendev.gravendevback.model.discord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscordUserResponse (
    @JsonProperty("id") Long id,
    @JsonProperty("username") String username,
    @JsonProperty("discriminator") String discriminator,
    @JsonProperty("email") String email,
    @JsonProperty("avatar") String avatarHash,
    @JsonProperty("banner") String bannerHash,
    @JsonProperty("accent_color") String accentColor
) {}
