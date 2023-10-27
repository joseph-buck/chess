package services.responses;

import java.util.*;


/**
 * ListGamesResponse --- Class for storing the response to a list games query.
 */
public class ListGamesResponse {
    private List<HashMap<String, Object>> games;
    private String message;
    private int code;

    public ListGamesResponse(ArrayList<HashMap<String, Object>> games,
                             String message, int code) {
        this.games = games;
        this.message = message;
        this.code = code;
    }

    public List<HashMap<String, Object>> getGames() {
        return games;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

}
