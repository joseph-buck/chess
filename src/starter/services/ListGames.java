package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import services.responses.*;

import java.util.*;


/**
 * ListGames --- Class providing an API for listing all games in the database.
 */
public class ListGames {

    public ListGamesResponse listGames(String reqHeader) {
        AuthDAO authDAOObj = new AuthDAO();
        GameDAO gameDAOObj = new GameDAO();
        String authTokenString;

        ArrayList<HashMap<String, Object>> games;

        String message;
        int code;

        if ((reqHeader == null) || (reqHeader.compareTo("") == 0)) {
            games = null;
            message = "Error: bad request";
            code = 400;
        } else {
            authTokenString = reqHeader;
            try {
                if (authDAOObj.readToken(authTokenString) == null) {
                    games = null;
                    message = "Error: unauthorized";
                    code = 401;
                } else {
                    games = toList(gameDAOObj.findAll());
                    message = null;
                    code = 200;
                }
            } catch (DataAccessException ex) {
                games = null;
                message = String.format("Error: DataAccessException thrown. \n%s", ex.toString());
                code = 500;
            }
        }
        return new ListGamesResponse(games, message, code);
    }

    private ArrayList<HashMap<String, Object>> toList (Set<Game> gamesSet) {
        ArrayList<HashMap<String, Object>> gamesList = new ArrayList<>();
        for (Game game : gamesSet) {
            HashMap<String, Object> gameMap = new HashMap<String, Object>();
            gameMap.put("gameID", game.getGameID());
            gameMap.put("whiteUsername", game.getWhiteUsername());
            gameMap.put("blackUsername", game.getBlackUsername());
            gameMap.put("gameName", game.getGameName());
            gamesList.add(gameMap);
        }
        return gamesList;
    }
}
