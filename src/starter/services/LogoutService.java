package services;

import responses.LogoutResponse;
import dataAccess.*;


/**
 * Logout --- Class providing an API for logging a User out of the chess system.
 */
public class LogoutService {
    public LogoutResponse logout(String reqHeader) {
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
                message = String.format("Error: DataAccessException thrown. \n%s", ex);
                code = 500;
            }
        }
        return new LogoutResponse(message, code);
    }
}
