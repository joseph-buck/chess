package requests;


/**
 * CreateGameRequest --- Class for storing data of a request to create a game.
 */
public class CreateGameRequest {
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

}
