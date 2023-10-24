package services.responses;


/**
 * LoginResponse --- Class for storing the data of a login response
 */
public class LoginResponse {
    /**
     * username - Identifier for the User that is logging in.
     */
    private String username;
    /**
     * authToken - Authorization token for user who is logging in.
     */
    private String authToken;
    /**
     * message - Error message.
     */
    private String message;

    /**
     * Default Constructor - Initializes all fields to null.
     */
    public LoginResponse() {
        this.username = null;
        this.authToken = null;
        this.message = null;
    }

    /**
     * Constructor - If an error message is supplied, the username and
     * authToken are initialized to null, and the error message is stored.
     * @param message User defined error message.
     */
    public LoginResponse(String message) {
        this.username = null;
        this.authToken = null;
        this.message = message;
    }

    /**
     * Constructor - If a username and authToken are supplied, they are stored
     * and the error message is set to null.
     * @param username User defined username.
     * @param authToken User defined authToken.
     */
    public LoginResponse(String username, String authToken) {
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
