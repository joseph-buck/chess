package service;

import service.request.*;
import service.response.*;


/**
 * Register --- Class providing an API for registering a new user with the
 * chess system.
 */
public class Register {
    /**
     * Method that registers a new User with the chess system. The method takes
     * in a request object, forwards it to the database, and returns a response
     * object from the database.
     * @param request The RegisterRequest object forwarded to the database.
     * @return Returns a RegisterResponse object from the database.
     */
    public RegisterResponse createGame(RegisterRequest request) {
        return new RegisterResponse();
    }
}
