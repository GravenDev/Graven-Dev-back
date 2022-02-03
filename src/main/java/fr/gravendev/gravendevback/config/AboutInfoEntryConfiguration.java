package fr.gravendev.gravendevback.config;

import fr.gravendev.gravendevback.entity.user.about.AboutInfoEntry;
import fr.gravendev.gravendevback.repository.user.about.AboutInfoEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AboutInfoEntryConfiguration {

    @Bean
    public CommandLineRunner fillAboutInfoEntry(AboutInfoEntryRepository aboutInfoEntryRepository) {
        return args -> populateAboutInfoEntry(aboutInfoEntryRepository);
    }

    public void populateAboutInfoEntry(AboutInfoEntryRepository aboutInfoEntryRepository) {
        if (aboutInfoEntryRepository.findAll().size() > 0) return;

        aboutInfoEntryRepository.saveAll(List.of(
                AboutInfoEntry.builder()
                        .name("Nom")
                        .icon("")
                        .position(1)
                        .build(),
                AboutInfoEntry.builder()
                        .name("Prénom")
                        .icon("")
                        .position(2)
                        .build(),
                AboutInfoEntry.builder()
                        .name("Pseudo")
                        .icon("")
                        .position(3)
                        .build(),
                AboutInfoEntry.builder()
                        .name("Age")
                        .icon("")
                        .position(4)
                        .build(),
                AboutInfoEntry.builder()
                        .name("Classe")
                        .icon("")
                        .position(5)
                        .build()));
    }
}
