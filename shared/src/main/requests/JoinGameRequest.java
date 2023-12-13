package requests;


import java.util.HashMap;
import java.util.Map;

/**
 * JoinGameRequest --- Class for storing data of a request to join a game.
 */
public class JoinGameRequest extends Request {
    private String playerColor;
    private Integer gameID;
    private String authToken;
    Map<String, Object> joinGameRequest = new HashMap<>();

    public JoinGameRequest(String playerColor, Integer gameID, String authToken) {
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.authToken = authToken;

        joinGameRequest.put("playerColor", playerColor);
        joinGameRequest.put("gameID", gameID);
        joinGameRequest.put("authToken", authToken);
    }

    public String getPlayerColor() {
        return playerColor;
        //return (String) joinGameRequest.get("playerColor");
    }

    public Integer getGameID() {
        return gameID;
        //var gameID = joinGameRequest.get("gameID");
        //System.out.println(gameID.getClass());
        //if (gameID.getClass().isInstance(java.lang.Integer.class)) {
        //    return ((Integer) gameID);
        //} else if (gameID.getClass().isInstance(java.lang.Double.class)) {
        //    return ((Integer) ((Double) gameID).intValue());
        //}
        //System.out.println("RETURNING NULL");
        //return null;
        //return -5;
    }

    public String getAuthToken() {
        return authToken;
        //return (String) joinGameRequest.get("authToken");
    }

    public Map getMap() {
        return joinGameRequest;
    }
}
