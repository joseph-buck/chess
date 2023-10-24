package services;

import services.requests.*;
import services.responses.*;

import spark.*;
import models.*;
import dataAccess.*;


/**
 * ClearApplication -- Class that provides an API for clearing the application.
 */
public class ClearApplication {
    public ClearApplicationResponse clearApplication() {
        UserDAO userDAOObj = new UserDAO();
        AuthDAO authDAOObj = new AuthDAO();
        GameDAO gameDAOObj = new GameDAO();

        String message;
        int code;

        try {
            userDAOObj.removeAllUsers();
            authDAOObj.removeAllTokens();
            gameDAOObj.removeAllGames();

            message = null;
            code = 200;
        } catch (DataAccessException ex) {
            message = String.format("Error: DataAccessException thrown. \n%s", ex.toString());
            code = 500;
        }
        return new ClearApplicationResponse(message, code);


    }
}
