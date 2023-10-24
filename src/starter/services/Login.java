package services;

import services.requests.*;
import services.responses.*;


/**
 * Login -- Class providing an API for logging a User into the chess system.
 */
public class Login {
    /**
     * Method that facilitates logging a User into the chess system. The
     * method takes in a LoginRequest object, forwards it to the database, and
     * returns a LoginResponse object.
     * @param request The LoginRequest object that gets forwarded to the
     *                database.
     * @return Returns a LoginResponse object from the database.
     */
    public LoginResponse login(LoginRequest request) {
        return new LoginResponse();
    }
}
