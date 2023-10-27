package dataAccess;

import java.util.Set;
import java.util.HashSet;
import models.Game;


/**
 * GameDAO --- Class for interacting with Game objects in the database.
 */
public class GameDAO {
    // games - temporarily using static data member to store Games. Will
    //          eventually store this data in a relational database.
    public static HashSet<Game> games = new HashSet<>();

    public void insertGame(Game newGame) throws DataAccessException {
        games.add(newGame);
    }

    public Game findGame(Integer gameID) throws DataAccessException {
        if (gameID != null) {
            for (Game game : games) {
                if (game.getGameID() == gameID) {
                    return game;
                }
            }
        }
        return null;
    }

    public Game findGame(String gameName) throws DataAccessException {
        for (Game game: games) {
            if (game.getGameName().compareTo(gameName) == 0) {
                return game;
            }
        }
        return null;
    }

    public Set<Game> findAll() throws DataAccessException {
        return games;
    }

    public void removeAllGames() throws DataAccessException {
        games = new HashSet<>();
    }
}
