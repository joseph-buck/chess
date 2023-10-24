package service.response;


/**
 * ClearApplicationResponse --- Class for storing data about a clear
 * application response.
 */
public class ClearApplicationResponse {
    /**
     * message - Error message
     */
    private String message;

    /**
     * Default Constructor - Initializes error message to null
     */
    public ClearApplicationResponse() {
        this.message = null;
    }

    /**
     * Constructor - Initializes error message to user defined value
     * @param message User defined error message
     */
    public ClearApplicationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
