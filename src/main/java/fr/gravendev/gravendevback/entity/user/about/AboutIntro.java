package fr.gravendev.gravendevback.entity.user.about;

import fr.gravendev.gravendevback.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_about_intro", indexes = @Index(columnList = "user_id"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutIntro {

    @Id
    @SequenceGenerator(name = "user_about_intro_sequence", sequenceName = "user_about_intro_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_about_intro_sequence")
    @Getter private final Long id = 0L;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @Getter @Setter private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Getter @Setter private String text;
}
