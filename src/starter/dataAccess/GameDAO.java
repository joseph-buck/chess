package dataAccess;

import models.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.*;


/**
 * GameDAO --- Class for interacting with Game objects in the database.
 */
public class GameDAO {
    // games - temporarily using static data member to store Games. Will
    //          eventually store this data in a relational database.
    public static HashSet<Game> games = new HashSet<>();

    public void initGameTable(Connection conn) throws DataAccessException {
        String createGameTable = """
                    CREATE TABLE IF NOT EXISTS game (
                    gameID SMALLINT AUTO_INCREMENT NOT NULL,
                    whiteUsername VARCHAR(255),
                    blackUsername VARCHAR(255),
                    gameName VARCHAR(255) NOT NULL,
                    game TEXT,
                    PRIMARY KEY (gameID),
                    FOREIGN KEY (whiteUsername) REFERENCES user(username),
                    FOREIGN KEY (blackUsername) REFERENCES user(username)
                    )""";
        try {
            PreparedStatement createGameTableStatement
                    = conn.prepareStatement(createGameTable);
            createGameTableStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

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
