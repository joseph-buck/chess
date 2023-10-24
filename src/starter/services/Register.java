package services;

import services.requests.*;
import services.responses.*;

import models.*;
import dataAccess.*;


/**
 * Register --- Class providing an API for registering a new user with the
 * chess system.
 */
public class Register {
    /**
     * Method that registers a new User with the chess system. The method takes
     * in a request object, forwards it to the database, and returns a response
     * object from the database.
     * @param request The RegisterRequest object forwarded to the database.
     * @return Returns a RegisterResponse object from the database.
     */
    public RegisterResponse register(RegisterRequest request) {
        UserDAO userDAOObj = new UserDAO();

        Object username = request.getUsername();
        Object password = request.getPassword();
        Object email = request.getEmail();

        String authToken;
        String message;
        int code;

        try {
            if ((username == null) || (((String)username).compareTo("") == 0)) {
                username = null;
                authToken = null;
                message = "Error: bad request";
                code = 400;
            } else if (userDAOObj.readUser((String)username) != null) {
                username = null;
                authToken = null;
                message = "Error: already taken";
                code = 403;
            } else {
                authToken = userDAOObj.insertUser(new User((String)username,
                        (String)password, (String)email)).getAuthToken();
                message = null;
                code = 200;
            }
            return new RegisterResponse((String)username, authToken, message, code);
        } catch(DataAccessException ex) {
            username = null;
            authToken = null;
            message = String.format("Error: DataAccessException thrown. \n%s", ex.toString());
            code = 500;
            return new RegisterResponse((String)username, authToken, message, code);
        }
    }
}
