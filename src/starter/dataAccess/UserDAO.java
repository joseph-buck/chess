package dataAccess;

import model.User;


/**
 * UserDAO --- Class for interacting with User objects in the database.
 */
public class UserDAO {
    /**
     * Method for inserting a new User object into the database.
     * @param newUser User object to be inserted.
     * @throws DataAccessException
     */
    public void insertUser(User newUser) throws DataAccessException {

    }

    /**
     * Method for retrieving a User object from the database.
     * @param username The username associated with User object to be read.
     * @return The User object being queried from the database.
     * @throws DataAccessException
     */
    public User readUser(String username) throws DataAccessException {
        return new User("","","");
    }

    /**
     * Method to update a User object in the database.
     * @param username The username associated with the User to be updated.
     * @param updatedUser The User object with updated fields.
     * @throws DataAccessException
     */
    public void updateUser(String username, User updatedUser) throws DataAccessException {

    }

    /**
     * Method to remove a User object from the database.
     * @param username The username associated with the User to be removed.
     * @throws DataAccessException
     */
    public void removeUser(String username) throws DataAccessException {

    }
}
