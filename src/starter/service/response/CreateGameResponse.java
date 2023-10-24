package service.response;


/**
 * CreateGameResponse --- Class for storing data about a create game response.
 */
public class CreateGameResponse {
    /**
     * message - Error message.
     */
    private String message;
    /**
     * gameID - Unique identifier of the game to create.
     */
    private int gameID;

    /**
     * Default Constructor - Initializes all fields to default values.
     */
    public CreateGameResponse() {
        this.message = null;
        this.gameID = -1;
    }

    /**
     * Constructor - If an integer is supplied, the error message is set to
     * null and the provided gameID is stored.
     * @param gameID
     */
    public CreateGameResponse(int gameID) {
        this.message = null;
        this.gameID = gameID;
    }

    /**
     * Constructor - If a string is supplied, the error message is stored and
     * the gameID is set to a null value.
     * @param message
     */
    public CreateGameResponse(String message) {
        this.message = message;
        this.gameID = -1;
    }

    public String getMessage() {
        return this.message;
    }

    public int getGameID() {
        return this.gameID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

}
