package fr.gravendev.gravendevback.entity.user;

import fr.gravendev.gravendevback.entity.DiscordToken;
import fr.gravendev.gravendevback.entity.authtoken.AuthToken;
import fr.gravendev.gravendevback.entity.user.about.AboutInfo;
import fr.gravendev.gravendevback.entity.user.about.AboutIntro;
import fr.gravendev.gravendevback.entity.user.role.UserRoles;
import fr.gravendev.gravendevback.entity.user.tag.UserTags;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Getter private final Long id = 0L;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter private Set<AuthToken> authTokens;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private DiscordToken discordToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter @Setter private UserDiscordInfo discordInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private Set<UserRoles> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private Set<UserTags> userTags;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private Set<AboutInfo> aboutInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private AboutIntro aboutIntro;
}
