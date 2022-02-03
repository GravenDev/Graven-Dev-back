package fr.gravendev.gravendevback.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public @Data class UserTagModel {

    private long id;
    private int position;
    private String name;
    private String color;
    private String icon;
    private UserModel user;
}
