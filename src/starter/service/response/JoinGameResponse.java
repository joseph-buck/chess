package service.response;


/**
 * JoinGameResponse --- Class for storing data for a join game response.
 */
public class JoinGameResponse {
    /**
     * message - Error message.
     */
    private String message;

    /**
     * Default Constructor - Sets error message to null.
     */
    public JoinGameResponse() {
        this.message = null;
    }

    /**
     * Constructor - If an error message is supplied, it is stored in message.
     * @param message The user defined message
     */
    public JoinGameResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
