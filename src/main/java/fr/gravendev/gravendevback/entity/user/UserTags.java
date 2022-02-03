package fr.gravendev.gravendevback.entity.user;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_tags", indexes = {@Index(columnList = "user_id")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTags {

    @Id
    @SequenceGenerator(name = "user_tags_sequence", sequenceName = "user_tags_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tags_sequence")
    @Getter private final Long id = 0L;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @Getter @Setter private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @Getter @Setter private Set<UserTagEntry> userTagEntries;
}
