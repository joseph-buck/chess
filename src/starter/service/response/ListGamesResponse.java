package service.response;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;


/**
 * ListGamesResponse --- Class for storing the response to a list games query.
 */
public class ListGamesResponse {
    /**
     * message - Error message.
     */
    private String message;
    /**
     * games - The list of all games in the database.
     */
    private List<Map<String, Object>> games = new ArrayList<>();

    /**
     * Default Constructor - Sets error message to null, and obtains the list
     * of games by calling createGamesList().
     */
    public ListGamesResponse() {
        this.message = null;
        this.games = createGamesList();
    }

    /**
     * Constructor - If an error message is supplied, it is stored in message.
     * @param message User defined error message.
     */
    public ListGamesResponse(String message) {
        this.message = message;
        this.games = null;
    }

    private List<Map<String, Object>> createGamesList() {
        return new ArrayList<Map<String, Object>>();
    }

    public List<Map<String, Object>> getGames() {
        return games;
    }

    public String getMessage() {
        return this.message;
    }

    public void setGames() {
        this.games = createGamesList();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
