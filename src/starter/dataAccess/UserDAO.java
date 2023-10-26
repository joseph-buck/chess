package dataAccess;

import models.*;
import java.util.*;


/**
 * UserDAO --- Class for interacting with User objects in the database.
 */
public class UserDAO {
    private static HashSet<User> users = new HashSet<>();

    /**
     * Method for inserting a new User object into the database.
     * @param newUser User object to be inserted.
     * @throws DataAccessException
     * @return newToken The new token associated with the new User.
     */
    public AuthToken insertUser(User newUser) throws DataAccessException {
        AuthDAO authDAOObj = new AuthDAO();
        AuthToken newToken = new AuthToken(newUser.getUsername());
        if (this.readUser(newUser.getUsername()) == null) {
            users.add(newUser);
            authDAOObj.insertToken(newToken);
            return newToken;
        } else {
            return null;
        }
    }

    /**
     * Method for retrieving a User object from the database.
     * @param username The username associated with User object to be read.
     * @return The User object being queried from the database.
     * @throws DataAccessException
     */
    public User readUser(String username) throws DataAccessException {
        for (User user : users) {
            if (user.getUsername().compareTo(username) == 0) {
                return user;
            }
        }
        return null;
    }

    /**
     * Method to update a User object in the database.
     * @param username The username associated with the User to be updated.
     * @param updatedUser The User object with updated fields.
     * @throws DataAccessException
     */
    //public void updateUser(String username, User updatedUser) throws DataAccessException {
    //    User userToUpdate = this.readUser(username);
    //    AuthDAO authDAOObj = new AuthDAO();
    //    AuthToken tokenToUpdate = authDAOObj.readToken(username);
    //    //TODO: Update all tokens for a given username
    //    if ((userToUpdate != null)
    //            && (this.readUser(updatedUser.getUsername()) == null)) {
    //        userToUpdate.setUsername(updatedUser.getUsername());
    //        userToUpdate.setPassword(updatedUser.getPassword());
    //        userToUpdate.setEmail(updatedUser.getEmail());
    //        AuthToken newToken = new AuthToken(tokenToUpdate.getAuthToken(),
    //                updatedUser.getUsername());
    //        authDAOObj.updateToken(updatedUser.getUsername(), newToken);
    //    }
    //    //TODO: Updates to games as well?
    //}

    ///**
     //* Method to remove a User object from the database.
    // * @param username The username associated with the User to be removed.
    // * @throws DataAccessException
    // */
    //public void removeUser(String username) throws DataAccessException {
    //    User userToRemove = this.readUser(username);
    //    AuthDAO authDAOobj = new AuthDAO();
    //    AuthToken tokenToRemove = authDAOobj.readToken(username);
    //    //TODO: Remove all tokens for a given username
    //    if (userToRemove != null) {
    //        users.remove(userToRemove);
    //        authDAOobj.removeToken(username);
    //    }
    //    //TODO: Updates to games as well?
    //}

    public HashSet<User> getUsers() throws DataAccessException {
        return users;
    }

    public void removeAllUsers() throws DataAccessException {
        users = new HashSet<User>();
    }
}
