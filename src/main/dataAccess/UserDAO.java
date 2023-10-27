package dataAccess;

import models.*;
import java.util.*;


/**
 * UserDAO --- Class for interacting with User objects in the database.
 */
public class UserDAO {
    // users - temporarily using static data member to store Users. Will
    //          eventually store this data in a relational database.
    private static HashSet<User> users = new HashSet<>();

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

    public User readUser(String username) throws DataAccessException {
        for (User user : users) {
            if (user.getUsername().compareTo(username) == 0) {
                return user;
            }
        }
        return null;
    }

    public HashSet<User> getUsers(){
        return users;
    }

    public void removeAllUsers() throws DataAccessException {
        users = new HashSet<>();
    }
}
