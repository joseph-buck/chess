package services;

import chess.ChessGame;
import dataAccess.*;

import models.Game;
import services.requests.*;
import services.responses.*;

import java.util.Random;


/**
 * CreateGame --- Class that provides an API for creating a new Game.
 */
public class CreateGame {

    public CreateGameResponse createGame(CreateGameRequest request) {
        AuthDAO authDAOObj = new AuthDAO();
        GameDAO gameDAOObj = new GameDAO();

        String gameName = request.getGameName();
        String authTokenString = request.getAuthToken();

        Integer gameID;
        String message;
        int code;

        try {
            if (gameDAOObj.findGame(gameName) != null) {
                gameID = null;
                message = "Error: bad request";
                code = 400;
            } else if (authDAOObj.readToken(authTokenString) == null) {
                gameID = null;
                message = "Error: unauthorized";
                code = 401;
            } else {
                //TODO: Function for getting random int
                Random random = new Random();
                do {
                    gameID = random.nextInt(9998) + 1;
                    if (gameID < 0) {
                        gameID *= -1;
                    }
                } while (gameDAOObj.findGame(gameID) != null);
                String username = authDAOObj.readToken(authTokenString).getUsername();
                //TODO: Automatically put the person creating the game as white?
                gameDAOObj.insertGame(new Game(gameID, null,
                        null, gameName, new chess.Game()));
                message = null;
                code = 200;
            }
        } catch (DataAccessException ex) {
            gameID = null;
            message = String.format("Error: DataAccessException thrown. \n%s", ex.toString());
            code = 500;
        }
        return new CreateGameResponse(gameID, message, code);
    }
}
