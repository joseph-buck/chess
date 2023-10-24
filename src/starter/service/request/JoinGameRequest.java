package service.request;


/**
 * JoinGameRequest --- Class for storing data of a request to join a game.
 */
public class JoinGameRequest {
    /**
     * playerColor - The color the user wants to be in the new game.
     */
    private String playerColor;
    /**
     * The unique integer identifier for the game.
     */
    private int gameID;
    /**
     * The user's authorization token allowing them to join the game.
     */
    private String authToken;

    /**
     * Constructor - Initializes fields with user defined values.
     * @param playerColor User defined playerColor.
     * @param gameID User defined gameID.
     * @param authToken User defined authToken.
     */
    public JoinGameRequest(String playerColor, int gameID, String authToken) {
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.authToken = authToken;
    }

    public String getPlayerColor() {
        return this.playerColor;
    }

    public int getGameID() {
        return this.gameID;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
