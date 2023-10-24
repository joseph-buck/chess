package service.response;


/**
 * RegisterResponse --- Class for storing data of a register response.
 */
public class RegisterResponse {
    /**
     * username - Identifier for the User that is registering.
     */
    private String username;
    /**
     * authToken - Authorization token for the Use who is registering.
     */
    private String authToken;
    /**
     * message - Error message.
     */
    private String message;

    /**
     * Default Constructor - Initializes all fields to null.
     */
    public RegisterResponse() {
        this.username = null;
        this.authToken = null;
        this.message = null;
    }

    /**
     * Constructor - If an error message is supplied, it is stored, and the
     * username and authToken are set to null.
     * @param message
     */
    public RegisterResponse(String message) {
        this.username = null;
        this.authToken = null;
        this.message = message;
    }

    /**
     * Constructor - If the username and authToken are supplied, they are
     * stored and the error message is set to null.
     * @param username
     * @param authToken
     */
    public RegisterResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
        this.message = null;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public String getMessage() {
        return this.message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
