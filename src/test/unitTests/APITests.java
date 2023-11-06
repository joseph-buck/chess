package unitTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import services.*;
import services.requests.*;
import services.responses.*;
import dataAccess.*;
import models.*;
import chess.ChessGame;


public class APITests {
    private static String username = "John123";
    private static String password = "connect";
    private static String email = "john123@gmail.com";
    private static User newUser;

    private static int gameID = 12345;
    private static String whiteUsername = "username";
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
    public void clearApplicationSuccessCase() throws DataAccessException {
        // Clear the application
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        createGame(newGame.getGameName(), (String)loginResponse.getAuthToken());
        clearApplication();

        assertTrue(authDAOObj.getTokens().isEmpty(), "Database still contains tokens.");
        assertTrue(userDAOObj.getUsers().isEmpty(), "Database still contains users.");
        assertTrue(gameDAOObj.findAll().isEmpty(), "Database still contains games.");
    }

    @Test
    public void createGameSuccessCase() {
        // Create a game normally
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        CreateGameResponse createGameResponse = createGame(
                newGame.getGameName(), (String)loginResponse.getAuthToken());

        assertNotNull(createGameResponse.getGameID(), "GameID returned is null.");
        assertNull(createGameResponse.getMessage(), "Message returned is not null.");
    }

    @Test
    public void createGameFailureCase() {
        // Create game with a bad authToken
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        CreateGameResponse createGameResponse = createGame(
                newGame.getGameName(), "aaaaaaaaaa");

        assertNull(createGameResponse.getGameID(), "GameID returned is not null");
        assertNotNull(createGameResponse.getMessage(), "Message returned is null");
    }

    @Test
    public void joinGameSuccessCase() {
        // Join a game normally
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        CreateGameResponse createGameResponse = createGame(
                newGame.getGameName(), (String)loginResponse.getAuthToken());
        JoinGameResponse joinGameResponse = joinGame("WHITE",
                createGameResponse.getGameID(),
                (String)loginResponse.getAuthToken());

        assertNull(joinGameResponse.getMessage(), "Message returned is not null");
    }

    @Test
    public void joinGameFailureCase() {
        // Join a game that has not been created
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        JoinGameResponse joinGameResponse = joinGame("WHITE", 00000,
                (String)loginResponse.getAuthToken());

        assertNotNull(joinGameResponse.getMessage(), "Message returned is null");
    }

    @Test
    public void listGamesSuccessCase() {
        // List games normally
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        createGame("game1", (String)loginResponse.getAuthToken());
        createGame("game2", (String)loginResponse.getAuthToken());
        createGame("game3", (String)loginResponse.getAuthToken());
        createGame("game4", (String)loginResponse.getAuthToken());
        createGame("game5", (String)loginResponse.getAuthToken());
        ListGamesResponse listGamesResponse = listGames(
                (String)loginResponse.getAuthToken());

        assertEquals(5, listGamesResponse.getGames().size(),
                "Number of games in the database was not correct");
    }

    @Test
    public void listGamesFailureCase() {
        // List games using a bad authToken
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        createGame("game1", (String)loginResponse.getAuthToken());
        createGame("game2", (String)loginResponse.getAuthToken());
        createGame("game3", (String)loginResponse.getAuthToken());
        createGame("game4", (String)loginResponse.getAuthToken());
        createGame("game5", (String)loginResponse.getAuthToken());
        ListGamesResponse listGamesResponse = listGames("aaaaaaaa");

        assertNull(listGamesResponse.getGames(),
                "List of Games returned was not null");
    }

    @Test
    public void loginSuccessCase() {
        // Login a user normally
        register(newUser);
        LoginResponse loginResponse = login(newUser);

        assertEquals(username, loginResponse.getUsername(),
                "Username returned was not expected");
        assertNotNull(loginResponse.getAuthToken(),
                "AuthToken returned was null");
        assertNull(loginResponse.getMessage(), "Message returned was not null");
    }

    @Test
    public void loginFailureCase() {
        // Login a user who is not registered
        LoginResponse loginResponse = login(newUser);

        assertNotEquals(username, loginResponse.getUsername(),
                "Username returned should not equal this.username");
        assertNull(loginResponse.getAuthToken(),
                "AuthToken returned was not null");
        assertNotNull(loginResponse.getMessage(), "Message returned was null");

    }

    @Test
    public void logoutSuccessCase() {
        // Logout a user normally
        register(newUser);
        LoginResponse loginResponse = login(newUser);
        LogoutResponse logoutResponse = logout(
                (String)loginResponse.getAuthToken());

        assertNull(logoutResponse.getMessage(), "Message returned was not null");
    }

    @Test
    public void logoutFailureCase() {
        // Logout a user that is not logged in (bad auth token)
        register(newUser);
        LogoutResponse logoutResponse = logout("aaaaaaaaaa");

        assertNotNull(logoutResponse.getMessage(), "Message returned was null");
    }

    @Test
    public void registerSuccessCase() {
        // Register a user normally
        RegisterResponse registerResponse = register(newUser);

        assertEquals(username, registerResponse.getUsername(), "Username returned was not correct");
        assertNotNull(registerResponse.getAuthToken(), "Returned a null authToken.");
        assertNull(registerResponse.getMessage(), "Non-null message was returned");
    }

    @Test
    public void registerFailureCase() {
        // Register a user with an invalid username
        RegisterResponse registerResponse = register(new User(null,"",""));

        assertNull(registerResponse.getUsername(), "Username returned was not null");
        assertNull(registerResponse.getAuthToken(), "AuthToken returned was not null");
        assertNotNull(registerResponse.getMessage(), "Messaged returned was null");
    }

    private static void clearApplication() {
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearApplication();
    }

    private RegisterResponse register(User newUser) {
        RegisterService registerService = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest(
                newUser.getUsername(), newUser.getPassword(), newUser.getEmail());
        return registerService.register(registerRequest);
    }

    private LoginResponse login(User newUser) {
        return new LoginService().login(new LoginRequest(
                newUser.getUsername(), newUser.getPassword()));
    }

    private CreateGameResponse createGame(String gameName, String authTokenString) {
        CreateGameService createGameService = new CreateGameService();
        CreateGameRequest createGameRequest = new CreateGameRequest(
                gameName, authTokenString);
        return createGameService.createGame(createGameRequest);
    }

    private LogoutResponse logout(String authTokenString) {
        return new LogoutService().logout(authTokenString);
    }

    private JoinGameResponse joinGame(String playerColor, Integer gameID, String authToken) {
        return new JoinGameService().joinGame(
                new JoinGameRequest(playerColor, gameID, authToken));
    }

    private ListGamesResponse listGames(String authTokenString) {
        return new ListGamesService().listGames(authTokenString);
    }
}
