package services.responses;


/**
 * JoinGameResponse --- Class for storing data for a join game response.
 */
public class JoinGameResponse {
    private String message;
    private int code;

    public JoinGameResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

}
