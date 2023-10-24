package dataAccess;

import models.*;
import java.util.*;


/**
 * AuthDAO --- Class for interacting with AuthToken objects in the database.
 */
public class AuthDAO {
    private static HashSet<AuthToken> tokens = new HashSet<AuthToken>();

    /**
     * Method for inserting new AuthToken objects into the database
     * @param newToken The new AuthToken object to be inserted
     * @throws DataAccessException
     */
    public void insertToken(AuthToken newToken) throws DataAccessException {
        tokens.add(newToken);
    }

    /**
     * Method for reading an AuthToken object from the database.
     * @param username The username associated with the object to be queried.
     * @return Returns the AuthToken object from the database.
     * @throws DataAccessException
     */
    public ArrayList<AuthToken> readToken(String username) throws DataAccessException {
        ArrayList<AuthToken> userTokens = new ArrayList<>();
        for (AuthToken token : tokens) {
            if (token.getUsername().compareTo(username) == 0) {
                userTokens.add(token);
            }
        }
        return userTokens;
    }

    /**
     * Method for updating the fields of an existing AuthToken object.
     * @param username The username associated with the object to be updated.
     * @param updatedToken The updated object that will replace the current
     *                     object in the database.
     * @throws DataAccessException
     */
    //public void updateToken(String username, AuthToken updatedToken) throws DataAccessException {
    //    AuthToken tokenToUpdate = this.readToken(username);
    //    if (tokenToUpdate != null) {
    //        tokenToUpdate.setAuthToken(updatedToken.getAuthToken());
    //        tokenToUpdate.setUsername(updatedToken.getUsername());
    //    }
    //}

    /**
     * Method for deleting an AuthToken object from the database.
     * @param username The username associated with the object to be deleted.
     * @throws DataAccessException
     */
    //public void removeToken(String username) throws DataAccessException {
    //    AuthToken tokenToRemove = this.readToken(username);
    //    if (tokenToRemove != null) {
    //        tokens.remove(tokenToRemove);
    //    }
    //}//

    public HashSet<AuthToken> getTokens() {
        return tokens;
    }

    public void removeAllTokens() {
        tokens = new HashSet<AuthToken>();
    }
}
