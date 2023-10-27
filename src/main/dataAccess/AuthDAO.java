package dataAccess;

import models.*;
import java.util.*;


/**
 * AuthDAO --- Class for interacting with AuthToken objects in the database.
 */
public class AuthDAO {
    // tokens - temporarily using static data member to store AuthTokens. Will
    //          eventually store this data in a relational database.
    private static HashSet<AuthToken> tokens = new HashSet<>();

    public void insertToken(AuthToken newToken) throws DataAccessException {
        tokens.add(newToken);
    }

    public AuthToken readToken(String authTokenString) throws DataAccessException {
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

    public HashSet<AuthToken> getTokens() {
        return tokens;
    }

    public void removeAllTokens() {
        tokens = new HashSet<>();
    }
}
