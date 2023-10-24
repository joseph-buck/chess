package services.requests;


/**
 * CreateGameRequest --- Class for storing data of a request to create a game.
 */
public class CreateGameRequest {
    /**
     * gameName - Human readable identifier of the game to be created.
     */
    private String gameName;
    /**
     * authToken - The authToken string to represent the game session.
     */
    private String authToken;

    /**
     * Constructor - Initializes fields with user defined values.
     * @param gameName User defined gameName
     * @param authToken User defined authToken
     */
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

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
