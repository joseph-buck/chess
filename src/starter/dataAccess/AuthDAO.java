package dataAccess;

import models.*;
import java.util.*;


/**
 * AuthDAO --- Class for interacting with AuthToken objects in the database.
 */
public class AuthDAO {
    private static HashSet<AuthToken> tokens = new HashSet<AuthToken>();

    public void insertToken(AuthToken newToken) throws DataAccessException {
        tokens.add(newToken);
    }

    public AuthToken readToken(String authTokenString) throws DataAccessException {
        //TODO: Change this to take advantage of fast find behavior of sets?
        for (AuthToken token : tokens) {
            if (token.getAuthToken().compareTo(authTokenString) == 0) {
                return token;
            }
        }
        return null;
    }

    public void removeToken(String authTokenString) throws DataAccessException {
        AuthToken tokenToRemove = this.readToken(authTokenString);
        if (tokenToRemove != null) {
            tokens.remove(tokenToRemove);
        }
    }

    public ArrayList<AuthToken> readUserTokens(String username) throws DataAccessException {
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


    public HashSet<AuthToken> getTokens() {
        return tokens;
    }

    public void removeAllTokens() {
        tokens = new HashSet<AuthToken>();
    }
}
