package fr.gravendev.gravendevback.model.discord;

public enum DiscordGrantType {
    REFRESH_TOKEN,
    AUTHORIZATION_CODE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
