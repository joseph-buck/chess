package dataAccess;

import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

//TODO: CONNECTIONS ARE NOT BEING CLOSED SOMEWHERE. GO THROUGH AND CLOSE THEM EVERYWHERE.
/**
 * AuthDAO --- Class for interacting with AuthToken objects in the database.
 */
public class AuthDAO {
    private Database database = new Database();

    private final String initAuthTable = """
            CREATE TABLE IF NOT EXISTS auth_token (
                authTokenString VARCHAR(255) NOT NULL,
                username VARCHAR(255) NOT NULL,
                PRIMARY KEY (authTokenString),
                FOREIGN KEY (username) REFERENCES user(username)
                )""";
    private final String insertAuthRow = """
            INSERT INTO auth_token(authTokenString, username)
            VALUES (?, ?);
            """;
    private final String getAuthRow = """
            SELECT * FROM auth_token WHERE authTokenString = ?;
            """;
    private final String removeAuthRow = """
            DELETE FROM auth_token WHERE authTokenString = ?;
            """;
    private final String getAllAuthRows = """
            SELECT * FROM auth_token;
            """;
    private final String clearAuthTable = """
            DELETE FROM auth_token;
            """;

    public void initAuthTable() throws DataAccessException {
        CloseableTuple tupleResult = executeStatement(initAuthTable, new ArrayList<>());
        tupleResult.close();
    }

    public void insertToken(AuthToken newToken) throws DataAccessException {
        CloseableTuple tupleResult = executeStatement(insertAuthRow,
                new ArrayList<>(Arrays.asList(newToken.getAuthToken(),
                        newToken.getUsername())));
        tupleResult.close();
    }

    public AuthToken readToken(String authTokenString) throws DataAccessException {
        AuthToken resultToken;
        try (CloseableTuple tupleResult = executeStatement(getAuthRow,
                new ArrayList<>(Arrays.asList(authTokenString)))) { // Using toValidString here
            if (tupleResult.getResult() == null) {
                resultToken = null;
            } else if (tupleResult.getResult().next()) {
                String resultAuthTokenString = tupleResult.getResult().getString("authTokenString");
                String resultUsername = tupleResult.getResult().getString("username");
                resultToken = new AuthToken(resultAuthTokenString, resultUsername);
            } else {
                resultToken = null;
            }
            return resultToken;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public void removeToken(String authTokenString) throws DataAccessException {
        CloseableTuple tupleResult = executeStatement(removeAuthRow,
                new ArrayList<>(Arrays.asList(authTokenString)));
        tupleResult.close();
    }

    public HashSet<AuthToken> getTokens() throws DataAccessException {
        HashSet<AuthToken> tokenList = new HashSet<>();
        try (CloseableTuple tupleResult = executeStatement(getAllAuthRows,
                new ArrayList<>())) {
            while (tupleResult.getResult().next()) {
                String resultAuthTokenString
                        = tupleResult.getResult().getString("authTokenString");
                String resultUsername = tupleResult.getResult().getString("username");
                tokenList.add(new AuthToken(resultAuthTokenString,
                        resultUsername));
            }
            return tokenList;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public void removeAllTokens() throws DataAccessException {
        CloseableTuple tupleResult = executeStatement(clearAuthTable, new ArrayList<>());
        tupleResult.close();
    }

    private CloseableTuple executeStatement(String sqlStatement, List<Object> params)
            throws DataAccessException {
        //TODO: Rollback transactions?
        /**
         * This is a function designed to take all the connection and
         * processing code out of the individual functions. It creates a
         * connection to the database, creates a prepared statement using
         * the supplied string and parameters, and executes the statement.
         * If the statement was a query, a ResultSet is returned. If it was
         * not a query, then null is returned.
         */
        try  {
            ResultSet result;
            Connection conn = database.getConnection();
            PreparedStatement preparedStatement
                    = conn.prepareStatement(sqlStatement);
            int iter = 1;
            for (Object object : params) {
                preparedStatement.setObject(iter, object);
                iter += 1;
            }
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            return new CloseableTuple(result, conn);
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
