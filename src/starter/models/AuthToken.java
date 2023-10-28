package models;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;

import java.util.UUID;


/**
 * AuthToken --- Class for storing authorization data of users.
 */
public class AuthToken {
    private String authToken;
    private String username;

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

}