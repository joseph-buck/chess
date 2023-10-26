package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import services.requests.*;
import services.responses.*;

import java.util.UUID;


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
        AuthDAO authDAOObj = new AuthDAO();
        UserDAO userDAOObj = new UserDAO();

        //TODO: Change these to Object type?
        String username = request.getUsername();
        String password = request.getPassword();

        String authTokenString;
        String message;
        int code;

        try {
            User userLoggingIn = userDAOObj.readUser(username);
            //TODO: Set username and password to null here, and only recover them later if necessary? Would need to make sure UserDAO would respond correcly when null or empty vals are supplied for username and password
            if ((username == null) || (username.compareTo("") == 0)                 // username is empty
                    || (password == null) || (password.compareTo("") == 0)) {       // password is empty
                username = null;
                authTokenString = null;
                message = "Error: bad request";
                code = 400;
            } else if ((userLoggingIn == null)                                      // username isn't recognized
                    || (userLoggingIn.getPassword().compareTo(password) != 0)) {    // password is incorrect
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
            message = String.format("Error: DataAccessException thrown. \n%s", ex.toString());
            code = 500;
        }
        return new LoginResponse(username, authTokenString, message, code);
    }
}
