package services.requests;


/**
 * LogoutRequest --- Class for storing logout request information
 */
public class LogoutRequest {
    /**
     * authToken - The token for the user submitting the logout request.
     */
    private String authToken;

    /**
     * Constructor - Initializes authToken field to that supplied by the user.
     * @param authToken User defined authToken.
     */
    public LogoutRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
