package fr.gravendev.gravendevback.config;

import fr.gravendev.gravendevback.entity.user.role.UserRoleEntry;
import fr.gravendev.gravendevback.repository.user.UserRoleEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserRoleEntryConfiguration {

    @Bean
    public CommandLineRunner fillRoles(UserRoleEntryRepository userRoleEntryRepository) {
        return args -> populateRoles(userRoleEntryRepository);
    }

    private void populateRoles(UserRoleEntryRepository userRoleEntryRepository) {
        if (userRoleEntryRepository.findAll().size() > 0) return;

        userRoleEntryRepository.saveAll(List.of(
                UserRoleEntry.builder()
                        .name("Administration")
                        .code("admin")
                        .color("ff2200")
                        .icon("mdi-star")
                        .discordId(793446836723646504L)
                        .position(1)
                        .build(),
                UserRoleEntry.builder()
                        .name("Modération")
                        .code("modo")
                        .color("9f48f7")
                        .icon("mdi-tools")
                        .discordId(783390457354649602L)
                        .position(2)
                        .build(),
                UserRoleEntry.builder()
                        .name("Référent")
                        .code("ref")
                        .color("00fdff")
                        .icon("mdi-book")
                        .discordId(795007262891704320L)
                        .position(3)
                        .build()
        ));
    }
}
