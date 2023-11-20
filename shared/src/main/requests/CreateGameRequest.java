package requests;


import java.util.HashMap;
import java.util.Map;

/**
 * CreateGameRequest --- Class for storing data of a request to create a game.
 */
public class CreateGameRequest extends Request {
    private String gameName;
    private String authToken;

    public CreateGameRequest(String gameName, String authToken) {
        this.gameName = gameName;
        this.authToken = authToken;
    }

    public String getGameName() {
        return this.gameName;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public Map getMap() {
        Map<String, Object> createGameRequest = new HashMap<>();
        createGameRequest.put("gameName", gameName);
        createGameRequest.put("authToken", authToken);
        return createGameRequest;
    }
}
