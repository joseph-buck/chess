package service.request;


/**
 * ListGamesRequest --- Class for storing data of a request to list all games.
 */
public class ListGamesRequest {
    /**
     * The authorization token for the user submitting the request.
     */
    private String authToken;

    /**
     * Constructor - Initializes the fields to user defined values.
     * @param authToken User defined authToken.
     */
    public ListGamesRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }}
