package service;

import service.request.*;
import service.response.*;


/**
 * CreateGame --- Class that provides an API for creating a new Game.
 */
public class CreateGame {
    /**
     * Method that creates a new game in the database. The method takes in a
     * request object, forwards the request to the database, and receives a
     * response object in return.
     * @param request The CreateGameRequest object that gets forwarded to the
     *                database.
     * @return Returns a CreateGameResponse object from the database.
     */
    public CreateGameResponse createGame(CreateGameRequest request) {
        return new CreateGameResponse();
    }
}
