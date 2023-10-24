package services.responses;


/**
 * LogoutResponse --- Class for storing the data of a logout response.
 */
public class LogoutResponse {
    /**
     * message - Error message.
     */
    private String message;

    /**
     * Default Constructor - Initializes the error message to null.
     */
    public LogoutResponse() {
        this.message = null;
    }

    /**
     * Constructor - Stores the supplied error message.
     * @param message User defined error message.
     */
    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
