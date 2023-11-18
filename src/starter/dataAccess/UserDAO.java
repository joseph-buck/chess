package dataAccess;

import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


/**
 * UserDAO --- Class for interacting with User objects in the database.
 */
public class UserDAO {
    private Database database = new Database();

    private final String initUserTable = """
            CREATE TABLE IF NOT EXISTS user (
                username VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )""";
    private final String insertUserRow = """
            INSERT INTO user(username, password, email)
            VALUES (?, ?, ?);
            """;
    private final String getUserRow = """
            SELECT * FROM user WHERE username = ?;
            """;
    private final String getAllUserRows = """
            SELECT * FROM user;
            """;
    private final String clearUserTable = """
            DELETE FROM user;
            """;

    public void initUserTable() throws DataAccessException {
        CloseableTuple tupleResult = executeStatement(initUserTable, new ArrayList<>());
        tupleResult.close();
    }

    public AuthToken insertUser(User newUser) throws DataAccessException {
        if (newUser == null) {
            return null;
        }
        AuthDAO authDAOObj = new AuthDAO();
        AuthToken newToken = new AuthToken(newUser.getUsername());
        if (this.readUser(newUser.getUsername()) == null) {
            CloseableTuple tupleResult = executeStatement(insertUserRow,
                    new ArrayList<>(Arrays.asList(newUser.getUsername(),
                            newUser.getPassword(), newUser.getEmail())));
            tupleResult.close();
            authDAOObj.insertToken(newToken);
            return newToken;
        } else {
            return null;
        }
    }

    public User readUser(String username) throws DataAccessException {
        User resultUser;
        try (CloseableTuple tupleResult = executeStatement(
                getUserRow, new ArrayList<>(Arrays.asList(username)))) {
            if (tupleResult.getResult() == null) {
                resultUser = null;
            } else if (tupleResult.getResult().next()) {
                String resultUsername = tupleResult.getResult().getString("username");
                String resultPassword = tupleResult.getResult().getString("password");
                String resultEmail = tupleResult.getResult().getString("email");
                resultUser = new User(resultUsername, resultPassword, resultEmail);
            } else {
                resultUser = null;
            }
            return resultUser;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public HashSet<User> getUsers() throws DataAccessException {
        //TODO: Merge this code and the code for readUser
        HashSet<User> userList = new HashSet<>();
        try (CloseableTuple tupleResult = executeStatement(getAllUserRows, new ArrayList<>())) {
            while (tupleResult.getResult().next()) {
                String resultUsername = tupleResult.getResult().getString("username");
                String resultPassword = tupleResult.getResult().getString("password");
                String resultEmail = tupleResult.getResult().getString("email");
                userList.add(new User(resultUsername,
                        resultPassword, resultEmail));
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
        return userList;
    }

    public void removeAllUsers() throws DataAccessException {
        CloseableTuple tupleResult = executeStatement(clearUserTable, new ArrayList<>());
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
            //TODO: May need to use the isQuery with an if statement for other functionality
            result = preparedStatement.getResultSet();
            return new CloseableTuple(result, conn);
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
