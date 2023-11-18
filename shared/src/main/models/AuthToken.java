package models;

import java.util.UUID;


/**
 * AuthToken --- Class for storing authorization data of users.
 */
public class AuthToken {
    private String authToken;
    private String username;

    public AuthToken(Object username) {
        this.authToken = UUID.randomUUID().toString();
        this.username = (String) username;
    }

    public AuthToken(Object authTokenString, Object username) {
        this.authToken = (String) authTokenString;
        this.username = (String) username;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public String getUsername() {
        return this.username;
    }

}
