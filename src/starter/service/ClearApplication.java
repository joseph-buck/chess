package service;

import service.request.*;
import service.response.*;


/**
 * ClearApplication -- Class that provides an API for clearing the application.
 */
public class ClearApplication {
    /**
     * Method that clears the database. This involves removing all User, Game,
     * and AuthToken objects from the database. The method receives a request
     * object, forwards the request object to the database, and receives a
     * response object in return.
     * @param request The ClearApplicationRequest object that gets forwarded to
     *                the database.
     * @return Returns a ClearApplicationResponse object from the database.
     */
    public ClearApplicationResponse login(ClearApplicationRequest request) {
        return new ClearApplicationResponse();
    }
}
