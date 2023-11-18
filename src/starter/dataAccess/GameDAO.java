package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import models.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


/**
 * GameDAO --- Class for interacting with Game objects in the database.
 */
public class GameDAO {
    private Database database = new Database();

    private final String initGameTable = """
            CREATE TABLE IF NOT EXISTS game (
            gameID SMALLINT AUTO_INCREMENT NOT NULL,
            whiteUsername VARCHAR(255),
            blackUsername VARCHAR(255),
            gameName VARCHAR(255) NOT NULL,
            game TEXT,
            PRIMARY KEY (gameID),
            FOREIGN KEY (whiteUsername) REFERENCES user(username),
            FOREIGN KEY (blackUsername) REFERENCES user(username)
            )""";//TODO: Rename game (the column) to chessGame
    private final String insertGameRow = """
            INSERT INTO game(gameID, whiteUsername, blackUsername, gameName, game)
            VALUES (?, ?, ?, ?, ?);
            """;
    private final String removeGameRow = """
            DELETE FROM game WHERE gameID = ?;
            """;
    private final String findGameByID = """
            SELECT * FROM game WHERE gameID = ?;
            """;
    private final String findGameByName = """
            SELECT * FROM game WHERE gameName = ?;
            """;
    private final String getAllGameRows = """
            SELECT * FROM game;
            """;
    private final String clearGameTable = """
            DELETE FROM game;
            """;

    public void initGameTable() throws DataAccessException {
        CloseableTuple tupleResult = executeStatement(initGameTable, new ArrayList<>());
        tupleResult.close();
    }

    public boolean insertGame(Game newGame) throws DataAccessException {
        Integer gameID = newGame.getGameID() < 0 ? null : newGame.getGameID();
        if (findGame(gameID) == null) {
            executeStatement(insertGameRow, new ArrayList<>(Arrays.asList(gameID,
                    newGame.getWhiteUsername(), newGame.getBlackUsername(), newGame.getGameName(),
                    new Gson().toJson(newGame.getChessGame()))));
            return true;
        }
        return false;
    }

    public boolean removeGame(int gameID) throws DataAccessException {
        if (findGame(gameID) != null) {
            executeStatement(removeGameRow, new ArrayList<>(Arrays.asList(gameID)));
            return true;
        }
        return false;
    }

    public Game findGame(Integer gameID) throws DataAccessException {
        Game resultGame;
        try (CloseableTuple tupleResult = executeStatement(findGameByID,
                new ArrayList<>(Arrays.asList(gameID)))) {
            if (tupleResult.getResult() == null) {
                resultGame = null;
            } else if (tupleResult.getResult().next()) {
                int resultGameID = tupleResult.getResult().getInt("gameID");
                String whiteUsername = tupleResult.getResult().getString("whiteUsername");
                String blackUsername = tupleResult.getResult().getString("blackUsername");
                String gameName = tupleResult.getResult().getString("gameName");
                //TODO: Use Gson here directly to convert string to ChessGame object.
                ChessGame game = toChessGame(tupleResult.getResult().getString("game"));
                resultGame = new Game(resultGameID, whiteUsername, blackUsername, gameName, game);
            } else {
                resultGame = null;
            }
            return resultGame;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public Game findGame(String gameName) throws DataAccessException {
        Game resultGame;
        try (CloseableTuple tupleResult = executeStatement(findGameByName,
                new ArrayList<>(Arrays.asList(gameName)))) {
            if (tupleResult.getResult() == null) {
                resultGame = null;
            } else if (tupleResult.getResult().next()) {
                int gameID = tupleResult.getResult().getInt("gameID");
                String whiteUsername = tupleResult.getResult().getString("whiteUsername");
                String blackUsername = tupleResult.getResult().getString("blackUsername");
                String resultGameName = tupleResult.getResult().getString("gameName");
                //TODO: Use Gson here directly to convert string to ChessGame object.
                ChessGame game = toChessGame(tupleResult.getResult().getString("game"));
                resultGame = new Game(gameID, whiteUsername, blackUsername, resultGameName, game);
            } else {
                resultGame = null;
            }
            return resultGame;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public Set<Game> findAll() throws DataAccessException {
        HashSet<Game> gameList = new HashSet<>();
        try (CloseableTuple tupleResult = executeStatement(getAllGameRows, new ArrayList<>())) {
            while (tupleResult.getResult().next()) {
                int gameID = tupleResult.getResult().getInt("gameID");
                String whiteUsername = tupleResult.getResult().getString("whiteUsername");
                String blackUsername = tupleResult.getResult().getString("blackUsername");
                String resultGameName = tupleResult.getResult().getString("gameName");
                ChessGame game = toChessGame(tupleResult.getResult().getString("game"));
                gameList.add(new Game(gameID, whiteUsername, blackUsername,
                        resultGameName, game));
            }
            return gameList;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public void removeAllGames() throws DataAccessException {
        executeStatement(clearGameTable, new ArrayList<>());
        //games = new HashSet<>();
    }

    private CloseableTuple executeStatement(String sqlStatement, List<Object> params)
            throws DataAccessException {
        //TODO: Rollback transactions?
        /**
         * This is a function designed to take all the connection and
         * processing code out of the individual functions. It creates a
         * connection to the database, creates a prepared statement using
         * the supplied string and parameters, and executes the statement.
         * If the statement was a query, a ResultSet is returned. If it was
         * not a query, then null is returned.
         */
        try  {
            ResultSet result;
            Connection conn = database.getConnection();
            PreparedStatement preparedStatement
                    = conn.prepareStatement(sqlStatement);
            int iter = 1;
            for (Object object : params) {
                preparedStatement.setObject(iter, object);
                iter += 1;
            }
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            return new CloseableTuple(result, conn);
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private ChessGame toChessGame(String gameString) {
        // Use Gson to turn the string into a chess.Game() object
        return new chess.Game();
    }
}
