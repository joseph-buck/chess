package dataAccess;

import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.*;


/**
 * AuthDAO --- Class for interacting with AuthToken objects in the database.
 */
public class AuthDAO {
    // tokens - temporarily using static data member to store AuthTokens. Will
    //          eventually store this data in a relational database.
    private static HashSet<AuthToken> tokens = new HashSet<>();

    public void initAuthTable(Connection conn) throws DataAccessException {
        String createAuthTokenTable = """
                    CREATE TABLE IF NOT EXISTS auth_token (
                        authTokenString CHAR(255),
                        username VARCHAR(255) NOT NULL,
                        PRIMARY KEY (authTokenString),
                        FOREIGN KEY (username) REFERENCES user(username)
                    )""";
        try {
            System.out.println("Creating authTable");
            PreparedStatement createAuthTokenTableStatement
                    = conn.prepareStatement(createAuthTokenTable);
            createAuthTokenTableStatement.executeUpdate();
            System.out.println("Created authTable");
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }


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
