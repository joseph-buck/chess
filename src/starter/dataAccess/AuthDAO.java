package dataAccess;

import model.AuthToken;


/**
 * AuthDAO --- Class for interacting with AuthToken objects in the database.
 */
public class AuthDAO {
    /**
     * Method for inserting new AuthToken objects into the database
     * @param newToken The new AuthToken object to be inserted
     * @throws DataAccessException
     */
    public void insertToken(AuthToken newToken) throws DataAccessException {

    }

    /**
     * Method for reading an AuthToken object from the database.
     * @param username The username associated with the object to be queried.
     * @return Returns the AuthToken object from the database.
     * @throws DataAccessException
     */
    public AuthToken readToken(String username) throws DataAccessException {
        return new AuthToken("", "");
    }

    /**
     * Method for updating the fields of an existing AuthToken object.
     * @param username The username associated with the object to be updated.
     * @param updatedToken The updated object that will replace the current
     *                     object in the database.
     * @throws DataAccessException
     */
    public void updateToken(String username, AuthToken updatedToken) throws DataAccessException {

    }

    /**
     * Method for deleting an AuthToken object from the database.
     * @param username The username associated with the object to be deleted.
     * @param updatedToken The AuthToken object with updated fields.
     * @throws DataAccessException
     */
    public void removeToken(String username, AuthToken updatedToken) throws DataAccessException {

    }
}
