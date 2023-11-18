package services;

import responses.ClearApplicationResponse;
import dataAccess.*;


/**
 * ClearApplication -- Class that provides an API for clearing the application.
 */
public class ClearApplicationService {
    public ClearApplicationResponse clearApplication() {
        UserDAO userDAOObj = new UserDAO();
        AuthDAO authDAOObj = new AuthDAO();
        GameDAO gameDAOObj = new GameDAO();

        String message;
        int code;

        try {
            authDAOObj.removeAllTokens();
            gameDAOObj.removeAllGames();
            userDAOObj.removeAllUsers();

            message = null;
            code = 200;
        } catch (DataAccessException ex) {
            message = String.format("Error: DataAccessException thrown. \n%s", ex);
            code = 500;
        }
        return new ClearApplicationResponse(message, code);
    }
}
