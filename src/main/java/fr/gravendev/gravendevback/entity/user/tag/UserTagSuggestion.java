package fr.gravendev.gravendevback.entity.user.tag;

import fr.gravendev.gravendevback.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_tag_suggestion", indexes = @Index(columnList = "suggested_by_id"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTagSuggestion {

    @Id
    @SequenceGenerator(name = "user_tag_suggestion_sequence", sequenceName = "user_tag_suggestion_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tag_suggestion_sequence")
    @Getter private final Long id = 0L;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    @Getter @Setter private User suggestedBy;

    @Column(length = 64, nullable = false)
    @Getter @Setter private String name;

    @Column(length = 64, nullable = true)
    @Getter @Setter private String icon;
}
