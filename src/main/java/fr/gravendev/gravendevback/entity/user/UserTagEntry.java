package fr.gravendev.gravendevback.entity.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_tag_entry")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTagEntry {

    @Id
    @SequenceGenerator(name = "user_tag_entry_sequence", sequenceName = "user_tag_entry_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tag_entry_sequence")
    @Getter private final Long id = 0L;

    @Column(nullable = false, unique = true)
    @Getter @Setter private int position;

    @Column(length = 64, nullable = false, unique = true)
    @Getter @Setter private String name;

    @Column(length = 6, nullable = true)
    @Getter @Setter private String color;

    @Column(length = 64, nullable = false)
    @Getter @Setter private String icon;
}
