package unitTests;

import chess.ChessGame;
import dataAccess.*;
import models.AuthToken;
import models.Game;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


//NOTE: I did not create tests for certain public functions of each DAO class
// that require other functions to be called in  a certain order. Examples are
// init functions and removeAll functions. Those are tested simultaneously.
public class DBTests {
    private static String username = "John123";
    private static String password = "connect";
    private static String email = "john123@gmail.com";
    private static User newUser;

    private static int gameID = 12345;
    private static String whiteUsername = null;
    private static String blackUsername = null;
    private static String gameName = "My Game";
    private static ChessGame chessGame = new chess.Game();
    private static Game newGame;

    private AuthDAO authDAOObj = new AuthDAO();
    private UserDAO userDAOObj = new UserDAO();
    private GameDAO gameDAOObj = new GameDAO();

    @BeforeEach
    public void init() {
        clearApplication();

        newUser = new User(username, password, email);
        newGame = new Game(gameID, whiteUsername, blackUsername,
                gameName, chessGame);
    }

    @Test
    public void insertUserSuccessCase() throws DataAccessException {
        // Insert user into database
        AuthToken returnToken = userDAOObj.insertUser(newUser);

        assertNotNull(returnToken, "Returned token was null.");
    }

    @Test
    public void insertUserFailureCase() throws DataAccessException {
        // Insert a user twice
        userDAOObj.insertUser(newUser);
        AuthToken returnToken = userDAOObj.insertUser(newUser);

        assertNull(returnToken, "Returned token was not null");
    }

    @Test
    public void readUserSuccessCase() throws DataAccessException {
        // Read a user in the database
        userDAOObj.insertUser(newUser);
        User returnUser = userDAOObj.readUser(newUser.getUsername());

        assertNotNull(returnUser, "Returned User was null");
    }

    @Test
    public void readUserFailureCase() throws DataAccessException {
        // Try to read a user that isn't in the database
        userDAOObj.insertUser(newUser);
        User returnUser = userDAOObj.readUser("asdf");

        assertNull(returnUser, "Returned User was not null");
    }

    @Test
    public void getUsersSuccessCase() throws DataAccessException {
        // Read users from database
        userDAOObj.insertUser(newUser);
        userDAOObj.insertUser(new User("j", "c", "a"));
        HashSet<User> returnUsers = userDAOObj.getUsers();

        assertEquals(2, returnUsers.size(), "Set was the incorrect size");
    }

    @Test
    public void getUsersFailureCase() throws DataAccessException {
        // Read users from a database that should be empty
        userDAOObj.insertUser(null);
        HashSet<User> returnUsers = userDAOObj.getUsers();

        assertEquals(0, returnUsers.size(), "Set was the incorrect size");
    }

    @Test
    public void initDatabaseSuccessCase() throws DataAccessException {
        // Initialize the database
        userDAOObj.initUserTable();
        authDAOObj.initAuthTable();
        gameDAOObj.initGameTable();

        assertNotNull(userDAOObj.getUsers());
        assertNotNull(authDAOObj.getTokens());
        assertNotNull(gameDAOObj.findAll());
    }

    @Test
    public void clearDatabaseSuccessCase() throws DataAccessException {
        // Clear the database
        userDAOObj.insertUser(newUser);

        gameDAOObj.removeAllGames();
        authDAOObj.removeAllTokens();
        userDAOObj.removeAllUsers();

        assertTrue(userDAOObj.getUsers().isEmpty(), "Users was not empty");
        assertTrue(authDAOObj.getTokens().isEmpty(), "Users was not empty");
        assertTrue(gameDAOObj.findAll().isEmpty(), "Users was not empty");
    }

    @Test
    public void insertTokenSuccessCase() throws DataAccessException {
        // Insert a token
        userDAOObj.insertUser(newUser);

        assertEquals(1, authDAOObj.getTokens().size(), "Incorrect number of tokens.");
    }

    @Test
    public void insertTokenFailureCase() throws DataAccessException {
        // Insert two of the same token
        AuthToken authToken = userDAOObj.insertUser(newUser);
        boolean insertSuccess = authDAOObj.insertToken(
                new AuthToken(authToken.getAuthToken(), authToken.getUsername()));

        assertFalse(insertSuccess, "Success message was not false.");
    }

    @Test
    public void readTokenSuccessCase() throws DataAccessException {
        // Read a token that is in the database
        AuthToken authToken = userDAOObj.insertUser(newUser);
        AuthToken resultToken = authDAOObj.readToken(authToken.getAuthToken());

        assertNotNull(resultToken, "Result token was null.");
    }

    @Test
    public void readTokenFailureCase() throws DataAccessException {
        // Read a token that is not in the database
        AuthToken resultToken = authDAOObj.readToken(
                new AuthToken("asdf").getAuthToken());

        assertNull(resultToken, "Result token was not null");
    }

    @Test
    public void removeTokenSuccessCase() throws DataAccessException {
        // Remove a token from the database
        AuthToken authToken = userDAOObj.insertUser(newUser);
        authDAOObj.removeToken(authToken.getAuthToken());

        assertNull(authDAOObj.readToken(authToken.getAuthToken()),
                "AuthToken is still in database");
    }

    @Test
    public void removeTokenFailureCase() throws DataAccessException {
        // Try to remove a token that is not in the database
        AuthToken authToken = userDAOObj.insertUser(newUser);
        authDAOObj.removeToken(authToken.getAuthToken());
        boolean removeSuccess = authDAOObj.removeToken(authToken.getAuthToken());

        assertFalse(removeSuccess, "Success message was not false.");
    }

    @Test
    public void getTokensSuccessCase() throws DataAccessException {
        // Get all tokens in the database
        userDAOObj.insertUser(newUser);
        HashSet<AuthToken> returnTokens = authDAOObj.getTokens();

        assertEquals(1, returnTokens.size(), "Set was the incorrect size");
    }

    @Test
    public void getTokensFailureCase() throws DataAccessException {
        // Try to get all tokens from an empty database
        HashSet<AuthToken> returnTokens = authDAOObj.getTokens();

        assertEquals(0, returnTokens.size(), "Database was not empty");
    }

    @Test
    public void insertGameSuccessCase() throws DataAccessException {
        // Insert a game into the database
        AuthToken authToken = userDAOObj.insertUser(newUser);
        boolean successInsert = gameDAOObj.insertGame(newGame);

        assertTrue(successInsert, "Success message was not true");
    }

    @Test
    public void insertGameFailureCase() throws DataAccessException {
        // Try to insert a game with an ID that is already in the database
        gameDAOObj.insertGame(newGame);
        boolean successInsert = gameDAOObj.insertGame(newGame);

        assertFalse(successInsert, "Success message was not false");
    }

    @Test
    public void removeGameSuccessCase() throws DataAccessException {
        // Remove a game from the database
        gameDAOObj.insertGame(newGame);
        gameDAOObj.removeGame(newGame.getGameID());

        assertTrue(gameDAOObj.findAll().isEmpty(), "Set wasn't empty");
    }

    @Test
    public void removeGameFailureCase() throws DataAccessException {
        // Try to remove a game that isn't in the database
        boolean successRemove = gameDAOObj.removeGame(1);

        assertFalse(successRemove, "Success message wasn't false");
    }

    @Test
    public void findGameSuccessCase() throws DataAccessException {
        // Test both by ID and by name
        gameDAOObj.insertGame(newGame);
        Game returnGame1 = gameDAOObj.findGame(newGame.getGameID());
        Game returnGame2 = gameDAOObj.findGame(newGame.getGameName());

        assertNotNull(returnGame1, "Game (found by ID) was null");
        assertNotNull(returnGame2, "Game (found by Name) was null");
    }

    @Test
    public void findGameFailureCase() throws DataAccessException {
        // Test both by ID and by name
        Game returnGame1 = gameDAOObj.findGame(newGame.getGameID());
        Game returnGame2 = gameDAOObj.findGame(newGame.getGameName());

        assertNull(returnGame1, "Game (found by ID) was not null");
        assertNull(returnGame2, "Game (found by Name) was not null");
    }

    @Test
    public void findAllSuccessCase() throws DataAccessException {
        // Find all games in the database
        gameDAOObj.insertGame(newGame);
        Set<Game> returnGames = gameDAOObj.findAll();

        assertFalse(returnGames.isEmpty(), "Set of games was empty");
    }

    @Test
    public void findAllFailureCase() throws DataAccessException {
        // Try to get tokens from an empty database
        Set<Game> returnGames = gameDAOObj.findAll();

        assertTrue(returnGames.isEmpty(), "Set of games was not empty");
    }

    private static void clearApplication() {
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearApplication();
    }
}
