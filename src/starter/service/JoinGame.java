package service;

import service.request.*;
import service.response.*;


/**
 * JoinGame --- Class that provides an API for joining a game.
 */
public class JoinGame {
    /**
     * Method that facilitates a User joining a Game. The method verifies that
     * the relevant game exists, and adds the User as either the white or black
     * team. If a color is not specified, the User joins as an observer. The
     * method takes in a request object, forwards the request to the database,
     * and returns a response object from the database.
     * @param request The JoinGameRequest object that gets forwarded to the
     *                database.
     * @return Returns a JoinGameRequest object from the database.
     */
    public JoinGameResponse createGame(JoinGameRequest request) {
        return new JoinGameResponse();
    }
}
