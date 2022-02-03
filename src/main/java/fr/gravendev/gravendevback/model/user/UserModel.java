package fr.gravendev.gravendevback.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class UserModel {

    private String email;

    private String discordId;
    private String tag;
    private Optional<String> nickname;
    private Optional<String> avatarUrl;
    private Optional<String> bannerUrl;
    private String accentColor;
    private List<UserRoleModel> roles;
    private List<UserTagModel> tags;
}
