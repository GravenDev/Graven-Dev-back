package fr.gravendev.gravendevback.model.login;

import fr.gravendev.gravendevback.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class LoginStatusModel {

    private boolean loggedIn;
    private UserModel user;
}
