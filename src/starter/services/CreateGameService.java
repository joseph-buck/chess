package services;

import models.Game;
import services.requests.*;
import services.responses.*;
import dataAccess.*;

import java.util.Random;


/**
 * CreateGame --- Class that provides an API for creating a new Game.
 */
public class CreateGameService {
    public CreateGameResponse createGame(CreateGameRequest request) {
        AuthDAO authDAOObj = new AuthDAO();
        GameDAO gameDAOObj = new GameDAO();

        Integer gameID;
        String message;
        int code;

        String gameName = request.getGameName();
        String authTokenString = request.getAuthToken();

        try {
            if (gameDAOObj.findGame(gameName) != null) {
                // Case: The gameName is already in the database.
                gameID = null;
                message = "Error: bad request";
                code = 400;
            } else if (authDAOObj.readToken(authTokenString) == null) {
                // Case: The provided authTokenString is not valid.
                gameID = null;
                message = "Error: unauthorized";
                code = 401;
            } else {
                //gameID = this.randInt();
                gameDAOObj.insertGame(new Game(-1, null,
                        null, gameName, new chess.Game()));
                gameID = gameDAOObj.findGame(gameName).getGameID();
                message = null;
                code = 200;
            }
        } catch (DataAccessException ex) {
            gameID = null;
            message = String.format("Error: DataAccessException thrown. \n%s", ex);
            code = 500;
        }
        return new CreateGameResponse(gameID, message, code);
    }

    // randInt - A helper function to create a randomly generated, unique gameID
    //TODO: Shouldn't need this function once database is implemented. This will be done internally.
    private int randInt() throws DataAccessException {
        int gameID;
        GameDAO gameDAOObj = new GameDAO();
        Random random = new Random();
        do {
            gameID = random.nextInt(9998) + 1;
        } while (gameDAOObj.findGame(gameID) != null);
        return gameID;
    }
}
