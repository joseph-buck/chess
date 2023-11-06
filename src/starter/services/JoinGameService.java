package services;

import models.*;
import services.requests.*;
import services.responses.*;
import dataAccess.*;


/**
 * JoinGame --- Class that provides an API for joining a game.
 */
public class JoinGameService {
    public JoinGameResponse joinGame(JoinGameRequest request) {
        AuthDAO authDAOObj = new AuthDAO();
        GameDAO gameDAOObj = new GameDAO();

        String message;
        int code;

        String playerColor = request.getPlayerColor();
        Integer gameID = request.getGameID();
        String authTokenString = request.getAuthToken();

        try {
            AuthToken authToken = authDAOObj.readToken(authTokenString);
            Game game = gameDAOObj.findGame(gameID);
            if (authToken == null) {
                // Case: The provided authTokenString is not valid
                message = "Error: unauthorized";
                code = 401;
            } else if (game == null) {
                // Case: The provided gameID was not found in the database
                message = "Error: bad request";
                code = 400;
            } else if (playerColor == null) {
                // Case: Request to join game as watcher
                message = null;
                code = 200;
            } else if (((playerColor.compareTo("WHITE") == 0)
                    && (game.getWhiteUsername() != null))
                    || ((playerColor.compareTo("BLACK") == 0)
                    && (game.getBlackUsername() != null))) {
                // Case: The requested playerColor is already occupied
                message = "Error: already taken";
                code = 403;
            } else {
                String username = authToken.getUsername();
                if (playerColor.compareTo("WHITE") == 0) {
                    game.setWhiteUsername(username);
                } else if (playerColor.compareTo("BLACK") == 0) {
                    game.setBlackUsername(username);
                }
                gameDAOObj.removeGame(game.getGameID());
                gameDAOObj.insertGame(game);
                message = null;
                code = 200;
            }
        } catch (DataAccessException ex) {
            message = String.format("Error: DataAccessException thrown. \n%s", ex);
            code = 500;
        }
        return new JoinGameResponse(message, code);
    }
}
