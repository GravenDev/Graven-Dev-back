package fr.gravendev.gravendevback.entity.user.role;

import fr.gravendev.gravendevback.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_roles", indexes = {@Index(columnList = "user_id")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles {

    @EmbeddedId
    @Getter private UserRolesKey id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @MapsId("userId")
    @JoinColumn(nullable = false)
    @Getter @Setter private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = false)
    @MapsId("userRoleId")
    @JoinColumn(nullable = false)
    @Getter @Setter private UserRoleEntry userRoleEntry;
}
