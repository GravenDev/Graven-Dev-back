package fr.gravendev.gravendevback.entity.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_discord_info", indexes = {@Index(columnList = "user_id"), @Index(columnList = "discordId")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDiscordInfo {

    @Id
    @SequenceGenerator(name = "user_discord_info_sequence", sequenceName = "user_discord_info_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_discord_info_sequence")
    @Getter private final Long id = 0L;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @Getter @Setter private User user;

    @Column(nullable = false, unique = true)
    @Getter private Long discordId;

    @Column(length = 128, nullable = true)
    @Getter @Setter private String email;

    @Column(length = 32, nullable = false) // max username length
    @Getter @Setter private String username;

    @Column(length = 4, nullable = false)
    @Getter @Setter private String discriminator;

    @Column(length = 32, nullable = true) // max username length
    @Getter @Setter private String nickname;

    @Column(length = 34, nullable = true) // 32 length hash + 2 for prefix
    @Getter @Setter private String avatarHash;

    @Column(length = 34, nullable = true) // 32 length hash + 2 for prefix
    @Getter @Setter private String bannerHash;

    @Column(length = 6, nullable = false)
    @Getter @Setter private String accentColor;
}
