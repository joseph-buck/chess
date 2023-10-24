package service;

import service.request.*;
import service.response.*;


/**
 * Logout --- Class providing an API for logging a User out of the chess system.
 */
public class Logout {
    /**
     * Method that logs a user out of the chess system. Takes in a
     * LogoutRequest object, forwards it to the database, and returns a
     * LogoutResponse object.
     * @param request The LogoutRequest object forwarded to the database.
     * @return Returns the LogoutResponse object from the database.
     */
    public LogoutResponse createGame(LogoutRequest request) {
        return new LogoutResponse();
    }
}
