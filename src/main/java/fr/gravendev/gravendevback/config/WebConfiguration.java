package fr.gravendev.gravendevback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfiguration {

    @Bean
    public WebClient discordWebClient() {
        return WebClient.create("https://discord.com/api/v9/");
    }

    @Bean
    public WebClient discordCdnClient() {
        return WebClient.create("https://cdn.discordapp.com");
    }
}
