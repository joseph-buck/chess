import chess.ChessGame;
import org.junit.jupiter.api.AfterAll;
import serverfacade.ServerFacade;
import requests.*;
import responses.*;
import com.google.gson.Gson;
import models.Game;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class FacadeTests {
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

    private static ServerFacade serverFacade;


    @BeforeEach
    public void init() {
        clearApplication();
        serverFacade = new ServerFacade();
    }

    @AfterAll
    public static void cleanup() {
        clearApplication();
    }

    @Test
    public void registerSuccessCase() {
        // Register a user normally
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);

        assertEquals(200, registerResponse.getCode(),
                "Code returned was not 200, 'success'.");
        assertNotEquals(0, ((String) registerResponse.getAuthToken()).length(),
                "AuthToken was null.");
    }

    @Test
    public void registerFailureCase() {
        // Attempt to register two people under the same username
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        serverFacade.register(registerRequest);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);

        assertEquals(403, registerResponse.getCode(),
                "Code returned was not 403, 'already taken'.");
        assertEquals(0, ((String) registerResponse.getAuthToken()).length(),
                "AuthToken was not empty.");
    }

    @Test
    public void loginSuccessCase() {
        // Login a user normally after registering
        RegisterRequest registerRequest = new RegisterRequest(
                "Doug", password, email);
        serverFacade.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("Doug", password);
        LoginResponse loginResponse = serverFacade.login(loginRequest);

        assertEquals(200, loginResponse.getCode(),
                "Code returned was not 200, 'successful'.");
        assertNotEquals(0, ((String) loginResponse.getAuthToken()).length(),
                "AuthToken was empty.");
    }

    @Test
    public void loginFailureCase() {
        // Attempt to log in a user who hasn't registered
        LoginRequest loginRequest = new LoginRequest("Doug", password);
        LoginResponse loginResponse = serverFacade.login(loginRequest);

        assertEquals(401, loginResponse.getCode(),
                "Code returned was not 401, 'Unauthorized'.");
        assertEquals(0, ((String) loginResponse.getAuthToken()).length(),
                "AuthToken was not empty.");
    }
    @Test
    public void createGameSuccessCase() {
        // Create a game with a user who has logged in
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = (String) registerResponse.getAuthToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(
                "myGame", authToken);
        CreateGameResponse createGameResponse
                = serverFacade.createGame(createGameRequest);

        assertEquals(200, createGameResponse.getCode(),
                "Code returned was not 200, 'Successful'.");
        assertNotEquals(0, createGameResponse.getGameID(),
                "ID returned was 0.");
    }

    @Test
    public void createGameFailureCase()  {
        // Create a game using an invalid AuthToken
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = "Bad Token";
        CreateGameRequest createGameRequest = new CreateGameRequest(
                "myGame", authToken);
        CreateGameResponse createGameResponse
                = serverFacade.createGame(createGameRequest);

        assertEquals(401, createGameResponse.getCode(),
                "Code returned was not 401, 'Unauthorized'.");
        assertEquals(0, createGameResponse.getGameID(),
                "Valid GameID was incorrectly produced.");
    }

    @Test
    public void listGamesSuccessCase() {
        // List games from a valid AuthToken
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = (String) registerResponse.getAuthToken();
        serverFacade.createGame(new CreateGameRequest(
                "myGame", authToken));
        serverFacade.createGame(new CreateGameRequest(
                "myGame1", authToken));
        ListGamesResponse listGamesResponse = serverFacade.listGames(authToken);

        assertEquals(200, listGamesResponse.getCode(),
                "Code returned was not 200, 'Successful'.");
        assertEquals(2, listGamesResponse.getGames().size(),
                "Games list had the wrong length.");
    }

    @Test
    public void listGamesFailureCase() {
        // List games from an invalid AuthToken
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = (String) registerResponse.getAuthToken();
        serverFacade.createGame(new CreateGameRequest(
                "myGame", authToken));
        serverFacade.createGame(new CreateGameRequest(
                "myGame1", authToken));
        ListGamesResponse listGamesResponse = serverFacade.listGames(
                "Bad Token");

        assertEquals(401, listGamesResponse.getCode(),
                "Code returned was not 401, 'Unauthorized'.");
        assertEquals(0, listGamesResponse.getGames().size(),
                "Games list had the wrong length.");
    }

    @Test
    public void joinGameSuccessCase() {
        // Join a game with a registered user
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = (String) registerResponse.getAuthToken();
        serverFacade.createGame(new CreateGameRequest(
                "myGame", authToken));
        HashMap<String, Object> game = new HashMap<>(serverFacade.listGames(authToken).getGames().get(0));
        int gameID = ((Double) game.get("gameID")).intValue();
        JoinGameResponse joinGameResponse = serverFacade.joinGame(new JoinGameRequest(
                "white", gameID, authToken));

        assertEquals(200, joinGameResponse.getCode(),
                "Code returned was not 200, 'Successful'");
    }

    @Test
    public void joinGameFailureCase() {
        // Attempt to join a game that doesn't exist with a registered user
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = (String) registerResponse.getAuthToken();
        JoinGameResponse joinGameResponse = serverFacade.joinGame(new JoinGameRequest(
                "white", 10, authToken));

        assertEquals(400, joinGameResponse.getCode(),
                "Code returned was not 400, 'Bad Request'.");
    }

    @Test
    public void logoutSuccessCase() {
        // Logout a user after logging in
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = (String) registerResponse.getAuthToken();
        LogoutResponse logoutResponse = serverFacade.logout(authToken);

        assertEquals(200, logoutResponse.getCode(),
                "Code returned was not 200, 'Successful'");
    }

    @Test
    public void logoutFailureCase() {
        // Logout a user who is not logged in
        RegisterRequest registerRequest = new RegisterRequest(
                username, password, email);
        RegisterResponse registerResponse = serverFacade.register(registerRequest);
        String authToken = (String) registerResponse.getAuthToken();
        serverFacade.logout(authToken);
        LogoutResponse logoutResponse = serverFacade.logout(authToken);

        assertEquals(401, logoutResponse.getCode(),
                "Code returned was not 401, 'Unauthorized'.");
    }

    private static void clearApplication() {
        try {
            URL url = new URL("http://localhost:8080/db");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("DELETE");
            connection.connect();
            try (var inputStream = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Map responseMap = new Gson().fromJson(inputStreamReader, Map.class);
                if (responseMap == null) {

                } else if (!responseMap.isEmpty()) {
                    String message = (String) responseMap.get("message");
                    Integer code = ((Double) responseMap.get("code")).intValue();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
