package services;

import models.*;
import requests.RegisterRequest;
import responses.RegisterResponse;
import dataAccess.*;


/**
 * Register --- Class providing an API for registering a new user with the
 * chess system.
 */
public class RegisterService {
    public RegisterResponse register(RegisterRequest request) {
        UserDAO userDAOObj = new UserDAO();

        String authTokenString;
        String message;
        int code;

        Object username = request.getUsername();
        Object password = request.getPassword();
        Object email = request.getEmail();

        try {
            if ((username == null) || (((String)username).compareTo("") == 0)
                    || (password == null)
                    || (((String)password).compareTo("") == 0)) {
                username = null;
                authTokenString = null;
                message = "Error: bad request";
                code = 400;
            } else if (userDAOObj.readUser((String)username) != null) {
                username = null;
                authTokenString = null;
                message = "Error: already taken";
                code = 403;
            } else {
                authTokenString = userDAOObj.insertUser(
                        new User((String)username, (String)password,
                                (String)email)).getAuthToken();
                message = null;
                code = 200;
            }
        } catch (DataAccessException ex) {
            username = null;
            authTokenString = null;
            message = String.format("Error: DataAccessException thrown. \n%s", ex);
            code = 500;
        }
        return new RegisterResponse(
                (String) username, authTokenString, message, code);
    }
}
