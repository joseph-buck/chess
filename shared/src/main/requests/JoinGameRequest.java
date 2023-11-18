package requests;


/**
 * JoinGameRequest --- Class for storing data of a request to join a game.
 */
public class JoinGameRequest {
    private String playerColor;
    private Integer gameID;
    private String authToken;

    public JoinGameRequest(String playerColor, Integer gameID, String authToken) {
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.authToken = authToken;
    }

    public String getPlayerColor() {
        return this.playerColor;
    }

    public Integer getGameID() {
        return this.gameID;
    }

    public String getAuthToken() {
        return this.authToken;
    }

}
