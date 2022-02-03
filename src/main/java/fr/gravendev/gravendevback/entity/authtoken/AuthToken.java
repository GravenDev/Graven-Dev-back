package fr.gravendev.gravendevback.entity.authtoken;

import fr.gravendev.gravendevback.entity.user.User;
import fr.gravendev.gravendevback.model.authtoken.Token;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "auth_tokens", indexes = {@Index(columnList = "user_id"), @Index(columnList = "authToken")})
public class AuthToken {

    public static final int AUTH_TOKEN_LENGTH = 64;

    @Id
    @SequenceGenerator(name = "auth_token_sequence", sequenceName = "auth_token_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_token_sequence")
    @Getter private final Long id = 0L;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(nullable = true)
    @Getter @Setter private User user;

    @Column(unique = true, nullable = false, length = AUTH_TOKEN_LENGTH)
    @Getter private final String authToken;

    @Column(nullable = false)
    @Getter @Setter private boolean valid = true;

    public AuthToken() {
        this(Token.create(AUTH_TOKEN_LENGTH).getToken());
    }

    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isLinked() {
        return user != null;
    }

    public boolean isLogged() {
        return isLinked() && isValid();
    }
}
