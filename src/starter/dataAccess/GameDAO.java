package dataAccess;

import java.util.Set;
import java.util.HashSet;
import model.Game;


/**
 * GameDAO --- Class for interacting with Game objects in the database.
 */
public class GameDAO {
    /**
     * Method for inserting new Game objects into the database.
     * @param newGame The new Game object to be inserted in the database.
     * @throws DataAccessException
     */
    public void insertGame(Game newGame) throws DataAccessException {

    }

    /**
     * Method for retrieving a Game object from the database.
     * @param gameID The ID of the Game object to be retrieved.
     * @return The Game object being retrieved.
     * @throws DataAccessException
     */
    public Game findGame(int gameID) throws DataAccessException {
        return new Game(gameID, "", "", "", new chess.Game());
    }

    /**
     * Method for retrieving all Game objects from the database.
     * @return A set containing all Game objects currently in the database.
     * @throws DataAccessException
     */
    public Set<Game> findAll() throws DataAccessException {
        return new HashSet<Game>();
    }

    /**
     * Method for claiming a spot in the game.
     * @param user
     */

    /**
     * Method for updating the fields of a Game object.
     * @param gameID The identifier of the Game to be updated.
     * @param updatedGame The Game object with updated fields.
     * @throws DataAccessException
     */
    public void updateGame(int gameID, Game updatedGame) throws DataAccessException {

    }

    /**
     * Method for removing an object from the database.
     * @param gameID The identifier of the Game to be removed.
     * @throws DataAccessException
     */
    public void removeGame(int gameID) throws DataAccessException {

    }

    /**
     * Method to remove all objects from the database. This includes all Game,
     * User, and AuthToken objects.
     * @throws DataAccessException
     */
    public void clearDatabase() throws DataAccessException {

    }

}
