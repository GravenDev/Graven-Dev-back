package fr.gravendev.gravendevback.entity.user.about;

import fr.gravendev.gravendevback.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_about_info", indexes = @Index(columnList = "user_id"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutInfo {

    @Id
    @SequenceGenerator(name = "user_about_info_sequence", sequenceName = "user_about_info_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_about_info_sequence")
    @Getter private final Long id = 0L;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @Getter @Setter private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @Getter @Setter private AboutInfoEntry aboutInfoEntry;

    @Column(length = 64, nullable = false)
    @Getter @Setter private String value;
}
