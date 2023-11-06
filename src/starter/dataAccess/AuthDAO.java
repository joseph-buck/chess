package dataAccess;

import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


/**
 * AuthDAO --- Class for interacting with AuthToken objects in the database.
 */
public class AuthDAO {
    // tokens - temporarily using static data member to store AuthTokens. Will
    //          eventually store this data in a relational database.
    private static HashSet<AuthToken> tokens = new HashSet<>();
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
            SELECT * FROM username WHERE username = ?;
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

    public void initAuthTable(Connection conn) throws DataAccessException {
        try (ResultSet ignored = executeStatement(
                initAuthTable, new ArrayList<>())) {
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }

    //public void initAuthTable(Connection conn) throws DataAccessException {
        //String createAuthTokenTable = """
        //            CREATE TABLE IF NOT EXISTS auth_token (
        //                authTokenString CHAR(255),
        //                username VARCHAR(255) NOT NULL,
        //                PRIMARY KEY (authTokenString),
        //                FOREIGN KEY (username) REFERENCES user(username)
        //            )""";
        //try {
        //    System.out.println("Creating authTable");
        //    PreparedStatement createAuthTokenTableStatement
        //            = conn.prepareStatement(createAuthTokenTable);
        //    createAuthTokenTableStatement.executeUpdate();
        //    System.out.println("Created authTable");
        //} catch (SQLException ex) {
        //    throw new DataAccessException(ex.getMessage());
        //}
    }

    public void insertToken(AuthToken newToken) throws DataAccessException {
        //try (ResultSet ignored = executeStatement(insertAuthRow,
        //        new ArrayList<>(Arrays.asList(newToken.getAuthToken(),
        //                newToken.getUsername())))) {
        //} catch (SQLException ex) {
        //    throw new DataAccessException(ex.getMessage());
        //}
        tokens.add(newToken);
    }

    //public void insertToken(AuthToken newToken) throws DataAccessException {
    //    tokens.add(newToken);
    //}

    public AuthToken readToken(String authTokenString) throws DataAccessException {
        AuthToken resultToken;
        //try (ResultSet result = executeStatement(getAuthRow,
        //        new ArrayList<>(Arrays.asList(authTokenString)))) {
        //    if (result == null) {
        //        resultToken = null;
        //    } else if (result.next()) {
        //        String resultAuthTokenString = result.getString("authTokenString");
        //        String resultUsername = result.getString("username");
        //        resultToken = new AuthToken(resultAuthTokenString, resultUsername);
        //    } else {
        //        resultToken = null;
        //    }
        //    return resultToken;
        //} catch (SQLException ex) {
        //    throw new DataAccessException(ex.getMessage());
        //}
        for (AuthToken token : tokens) {
            if (token.getAuthToken().compareTo(authTokenString) == 0) {
                return token;
            }
        }
        return null;
    }

    public void removeToken(String authTokenString) throws DataAccessException {
        //try (ResultSet result = executeStatement(removeAuthRow,
        //        new ArrayList<>(Arrays.asList(authTokenString)))) {
        //} catch (SQLException ex) {
        //    throw new DataAccessException(ex.getMessage());
        //}

        AuthToken tokenToRemove = this.readToken(authTokenString);
        if (tokenToRemove != null) {
            tokens.remove(tokenToRemove);
        }
    }

    public HashSet<AuthToken> getTokens() throws DataAccessException {
        //HashSet<AuthToken> tokenList = new HashSet<>();
        //try (ResultSet result = executeStatement(getAllAuthRows,
        //        new ArrayList<>())) {
        //    while (result.next()) {
        //        String resultAuthTokenString
        //                = result.getString("authTokenString");
        //        String resultUsername = result.getString("username");
        //        tokenList.add(new AuthToken(resultAuthTokenString,
        //                resultUsername));
        //    }
        //    return tokenList;
        //} catch (SQLException ex) {
        //    throw new DataAccessException(ex.getMessage());
        //}
        return tokens;
    }

    public void removeAllTokens() throws DataAccessException {
        //try (ResultSet ignored = executeStatement(
        //        clearAuthTable, new ArrayList<>())) {
        //} catch (SQLException ex) {
        //    throw new DataAccessException(ex.getMessage());
        //}
        tokens = new HashSet<>();
    }

    private ResultSet executeStatement(String sqlStatement, List<Object> params)
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
        ResultSet result;
        try  {
            Connection conn = database.getConnection();
            PreparedStatement preparedStatement
                    = conn.prepareStatement(sqlStatement);
            int iter = 1;
            for (Object object : params) {
                preparedStatement.setObject(iter, object);
                iter += 1;
            }
            preparedStatement.execute();
            //TODO: May need to use the isQuery with an if statement for other functionality
            result = preparedStatement.getResultSet();
            return result;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
