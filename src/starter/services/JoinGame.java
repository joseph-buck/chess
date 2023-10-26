package services;

import chess.ChessGame;
import dataAccess.*;
import models.*;
import services.requests.*;
import services.responses.*;
import chess.ChessGame.TeamColor;


/**
 * JoinGame --- Class that provides an API for joining a game.
 */
public class JoinGame {

    public JoinGameResponse joinGame(JoinGameRequest request) {
        AuthDAO authDAOObj = new AuthDAO();
        UserDAO userDAOObj = new UserDAO();
        GameDAO gameDAOObj = new GameDAO();

        String playerColor = request.getPlayerColor();
        Integer gameID = request.getGameID();
        String authTokenString = request.getAuthToken();

        String message;
        int code;

        //System.out.println(playerColor);
        //System.out.println(gameID);
        //System.out.println(authTokenString);

        // If authToken is not in database, 401 unauthorized
        // If game does not exist, 400 bad request
        // If color is not specified, join as observer
            // WHAT DOES IT MEAN TO JOIN AS AN OBSERVER???
        // If requested color is occupied, 403 already taken
        // If requested color is not occupied, join as player
            // Set whiteUsername or blackUsername of game to the supplied player color
        try {
            AuthToken authToken = authDAOObj.readToken(authTokenString);
            Game game = gameDAOObj.findGame(gameID);
            if (authToken == null) {
                message = "Error: unauthorized";
                code = 401;
            } else if (game == null) {
                message = "Error: bad request";
                code = 400;
            } else if (playerColor == null) {
                //TODO: What do I do if the player is joining as an observer?
                String username = authToken.getUsername();
                message = null;
                code = 200;
            } else if (((playerColor.compareTo("WHITE") == 0)
                    && (game.getWhiteUsername() != null))
                    || ((playerColor.compareTo("BLACK") == 0)
                    && (game.getBlackUsername() != null))) {
                message = "Error: already taken";
                code = 403;
            } else {
                String username = authToken.getUsername();
                if (playerColor.compareTo("WHITE") == 0) {
                    game.setWhiteUsername(username);
                } else if (playerColor.compareTo("BLACK") == 0) {
                    game.setBlackUsername(username);
                }
                message = null;
                code = 200;
            }
        } catch (DataAccessException ex) {
            message = String.format("Error: DataAccessException thrown. \n%s", ex.toString());
            code = 500;
        }
        return new JoinGameResponse(message, code);
    }
}
