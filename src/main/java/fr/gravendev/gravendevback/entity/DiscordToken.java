package fr.gravendev.gravendevback.entity;

import fr.gravendev.gravendevback.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "discord_tokens")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscordToken {

    @Id
    @SequenceGenerator(name = "discord_token_sequence", sequenceName = "discord_token_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discord_token_sequence")
    @Getter private final Long id = 0L;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @Getter @Setter private User user;

    @Column(nullable = false)
    @Getter @Setter private String accessToken;

    @Column(nullable = false)
    @Getter @Setter private String refreshToken;

    @Column(nullable = false)
    @Getter @Setter private Instant expiresAt;
}
