package services;

import models.*;
import services.requests.*;
import services.responses.*;
import dataAccess.*;


/**
 * Login -- Class providing an API for logging a User into the chess system.
 */
public class LoginService {
    public LoginResponse login(LoginRequest request) {
        AuthDAO authDAOObj = new AuthDAO();
        UserDAO userDAOObj = new UserDAO();

        String username = request.getUsername();
        String password = request.getPassword();

        String authTokenString;
        String message;
        int code;

        try {
            User userLoggingIn = userDAOObj.readUser(username);
            if ((username == null) || (username.compareTo("") == 0)
                    || (password == null) || (password.compareTo("") == 0)) {
                // Case: Username or password is empty or null
                username = null;
                authTokenString = null;
                message = "Error: bad request";
                code = 400;
            } else if ((userLoggingIn == null)
                    || (userLoggingIn.getPassword().compareTo(password) != 0)) {
                // Case: The supplied username was not in the database, or the
                //          password was incorrect
                username = null;
                authTokenString = null;
                message = "Error: unauthorized";
                code = 401;
            } else {
                AuthToken newAuthToken = new AuthToken(username);
                authDAOObj.insertToken(newAuthToken);
                authTokenString = newAuthToken.getAuthToken();
                message = null;
                code = 200;
            }
        } catch (DataAccessException ex) {
            username = null;
            authTokenString = null;
            message = String.format("Error: DataAccessException thrown. \n%s", ex);
            code = 500;
        }
        return new LoginResponse(username, authTokenString, message, code);
    }
}
