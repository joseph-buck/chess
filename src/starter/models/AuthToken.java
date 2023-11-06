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

        // We are not supposed to access DAO objects from model classes.
        //try {
        //    authDAO.insertToken(this);
        //} catch (DataAccessException ex) {
        //    this.authToken = null;
        //    this.username = null;
        //}
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
