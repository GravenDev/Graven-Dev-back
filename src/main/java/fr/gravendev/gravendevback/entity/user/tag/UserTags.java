package fr.gravendev.gravendevback.entity.user.tag;

import fr.gravendev.gravendevback.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_tags", indexes = {@Index(columnList = "user_id")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTags {

    @EmbeddedId
    @Getter private UserTagsKey id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @MapsId("userTagId")
    @JoinColumn(name = "user_tag_id", nullable = false)
    @Getter @Setter private UserTagEntry userTagEntry;
}
