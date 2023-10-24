package service;

import service.request.*;
import service.response.*;


/**
 * ListGames --- Class providing an API for listing all games in the database.
 */
public class ListGames {
    /**
     * Method that gives a list of all games currently in the database. The
     * method takes in a request object, forwards the request to the database,
     * and returns a response object.
     * @param request The ListGamesRequest object that gets forwarded to the
     *                database.
     * @return Returns a ListGamesResponse object from the database.
     */
    public ListGamesResponse createGame(ListGamesRequest request) {
        return new ListGamesResponse();
    }
}
