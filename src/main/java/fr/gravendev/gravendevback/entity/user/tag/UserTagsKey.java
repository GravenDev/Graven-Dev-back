package fr.gravendev.gravendevback.entity.user.tag;

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
public class UserTagsKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "user_tag_id")
    Long userTagId;
}
