package requests;


import java.util.HashMap;
import java.util.Map;

/**
 * JoinGameRequest --- Class for storing data of a request to join a game.
 */
public class JoinGameRequest extends Request {
    //private String playerColor;
    //private Integer gameID;
    //private String authToken;
    Map<String, Object> joinGameRequest = new HashMap<>();

    public JoinGameRequest(String playerColor, Integer gameID, String authToken) {
        //this.playerColor = playerColor;
        //this.gameID = gameID;
        //this.authToken = authToken;
        joinGameRequest.put("playerColor", playerColor);
        joinGameRequest.put("gameID", gameID);
        joinGameRequest.put("authToken", authToken);
    }

    public String getPlayerColor() {
        return (String) joinGameRequest.get("playerColor");
    }

    public Integer getGameID() {
        return ((Double) joinGameRequest.get("gameID")).intValue();
    }

    public String getAuthToken() {
        return (String) joinGameRequest.get("authToken");
    }

    public Map getMap() {
        return joinGameRequest;
    }
}
