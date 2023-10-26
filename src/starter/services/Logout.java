package services;

import dataAccess.*;
import models.*;
import services.responses.*;

import java.util.*;


/**
 * Logout --- Class providing an API for logging a User out of the chess system.
 */
public class Logout {

    public <T> LogoutResponse logout(String reqHeader) {
        // Get the authToken out of the reqHeader set
        // Check that the auth token is in the database
            // If it is, remove it.
            // If it isn't, 401 unauthorized

        AuthDAO authDAOObj = new AuthDAO();
        String authTokenString;

        String message;
        int code;

        if ((reqHeader == null) || (reqHeader.compareTo("") == 0)) {
            message = "Error: Bad request";
            code = 400;
        } else {
            authTokenString = reqHeader;
            try{
                if (authDAOObj.readToken(authTokenString) == null) {
                    message = "Error: unauthorized";
                    code = 401;
                } else {
                    authDAOObj.removeToken(authTokenString);
                    message = null;
                    code = 200;
                }
            } catch (DataAccessException ex) {
                message = String.format("Error: DataAccessException thrown. \n%s", ex.toString());
                code = 500;
            }
        }
        return new LogoutResponse(message, code);
    }
}
