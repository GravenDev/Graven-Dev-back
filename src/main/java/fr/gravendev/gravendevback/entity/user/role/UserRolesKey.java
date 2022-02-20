package fr.gravendev.gravendevback.entity.user.role;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class UserRolesKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "user_role_id")
    Long userRoleId;
}
