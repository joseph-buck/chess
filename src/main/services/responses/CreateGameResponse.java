package services.responses;


/**
 * CreateGameResponse --- Class for storing data about a create game response.
 */
public class CreateGameResponse {
    private Integer gameID;
    private String message;
    private int code;

    public CreateGameResponse(Integer gameID, String message, int code) {
        this.gameID = gameID;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getGameID() {
        return this.gameID;
    }

    public Integer getCode() {
        return this.code;
    }

}