package models;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;

import java.util.UUID;


/**
 * AuthToken --- Class for storing authorization data of users.
 */
public class AuthToken {
    /**
     * authToken - Holds the authentication code for a user's current session.
     */
    private String authToken;
    /**
     * username - The identifier of the user associated with the AuthToken.
     */
    private String username;

    /**
     * Constructor - Sets the authToken code and username for the new token.
     * @param username Username to be associated with the authToken code
     */
    public AuthToken(Object username) {
        AuthDAO authDAO = new AuthDAO();
        this.authToken = UUID.randomUUID().toString();
        this.username = (String) username;
        try {
            authDAO.insertToken(this);
        } catch (DataAccessException ex) {
            this.authToken = null;
            this.username = null;
        }

    }

    public String getAuthToken() {
        return this.authToken;
    }

    public String getUsername() {
        return this.username;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
