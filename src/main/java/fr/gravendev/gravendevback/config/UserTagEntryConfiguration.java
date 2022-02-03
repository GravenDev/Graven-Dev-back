package fr.gravendev.gravendevback.config;

import fr.gravendev.gravendevback.entity.user.UserTagEntry;
import fr.gravendev.gravendevback.repository.user.UserTagEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserTagEntryConfiguration {

    @Bean
    public CommandLineRunner fillTags(UserTagEntryRepository userTagEntryRepository) {
        return args -> populateTags(userTagEntryRepository);
    }

    private void populateTags(UserTagEntryRepository userTagEntryRepository) {
        if (userTagEntryRepository.findAll().size() > 0) return;

        userTagEntryRepository.saveAll(List.of(
                UserTagEntry.builder()
                        .name("Lead développeur")
                        .color("ffbb00")
                        .icon("mdi-account-group")
                        .position(2)
                        .build(),
                UserTagEntry.builder()
                        .name("Senior")
                        .color("ffbb00")
                        .icon("mdi-account-star")
                        .position(3)
                        .build(),
                UserTagEntry.builder()
                        .name("Junior")
                        .color("ffdd00")
                        .icon("mdi-account-supervisor")
                        .position(5)
                        .build(),
                UserTagEntry.builder()
                        .name("Python")
                        .icon("mdi-language-python")
                        .position(20)
                        .build(),
                UserTagEntry.builder()
                        .name("Java")
                        .icon("mdi-language-java")
                        .position(21)
                        .build(),
                UserTagEntry.builder()
                        .name("Kotlin")
                        .icon("mdi-language-kotlin")
                        .position(22)
                        .build(),
                UserTagEntry.builder()
                        .name("Html")
                        .icon("mdi-language-html5")
                        .position(23)
                        .build(),
                UserTagEntry.builder()
                        .name("Css")
                        .icon("mdi-language-css3")
                        .position(24)
                        .build(),
                UserTagEntry.builder()
                        .name("JavaScript")
                        .icon("mdi-language-javascript")
                        .position(25)
                        .build(),
                UserTagEntry.builder()
                        .name("TypeScript")
                        .icon("mdi-language-typescript")
                        .position(26)
                        .build(),
                UserTagEntry.builder()
                        .name("Php")
                        .icon("mdi-language-php")
                        .position(30)
                        .build(),
                UserTagEntry.builder()
                        .name("Ruby")
                        .icon("mdi-language-ruby")
                        .position(31)
                        .build(),
                UserTagEntry.builder()
                        .name("C")
                        .icon("mdi-language-c")
                        .position(32)
                        .build(),
                UserTagEntry.builder()
                        .name("C++")
                        .icon("mdi-language-cpp")
                        .position(33)
                        .build(),
                UserTagEntry.builder()
                        .name("Rust")
                        .icon("mdi-language-rust")
                        .position(34)
                        .build(),
                UserTagEntry.builder()
                        .name("Go")
                        .icon("mdi-language-go")
                        .position(35)
                        .build(),
                UserTagEntry.builder()
                        .name("Web front")
                        .icon("mdi-application-brackets-outline")
                        .position(50)
                        .build(),
                UserTagEntry.builder()
                        .name("Web back")
                        .icon("mdi-application-cog-outline")
                        .position(51)
                        .build(),
                UserTagEntry.builder()
                        .name("Linux")
                        .icon("mdi-linux")
                        .position(52)
                        .build(),
                UserTagEntry.builder()
                        .name("SysAdmin")
                        .icon("mdi-server")
                        .position(53)
                        .build()
        ));
    }
}
