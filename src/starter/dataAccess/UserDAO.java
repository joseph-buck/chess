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
    //private static HashSet<User> users = new HashSet<>();
    private Database database = new Database();

    private final String initUserTable = """
            CREATE TABLE IF NOT EXISTS user (
                username VARCHAR(255) NOT NULL,
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
        try (ResultSet ignored = executeStatement(
                initUserTable, new ArrayList<>())) {
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public AuthToken insertUser(User newUser) throws DataAccessException {
        AuthDAO authDAOObj = new AuthDAO();
        AuthToken newToken = new AuthToken(newUser.getUsername());
        if (this.readUser(newUser.getUsername()) == null) {
            try (ResultSet ignored = executeStatement(insertUserRow,
                    new ArrayList<>(Arrays.asList(newUser.getUsername(),
                            newUser.getPassword(), newUser.getEmail())))) {
            } catch (SQLException ex) {
                throw new DataAccessException(ex.getMessage());
            }
            authDAOObj.insertToken(newToken);
            return newToken;
        } else {
            return null;
        }
    }

    //public AuthToken insertUser(User newUser) throws DataAccessException {
    //    AuthDAO authDAOObj = new AuthDAO();
    //    AuthToken newToken = new AuthToken(newUser.getUsername());
    //    if (this.readUser(newUser.getUsername()) == null) {
    //        users.add(newUser);
    //        authDAOObj.insertToken(newToken);
    //        return newToken;
    //    } else {
    //        return null;
    //    }
    //}

    public User readUser(String username) throws DataAccessException {
        User resultUser;
        try (ResultSet result = executeStatement(
                getUserRow, new ArrayList<>(Arrays.asList(username)))) {
            if (result == null) {
                resultUser = null;
            } else if (result.next()) {
                String resultUsername = result.getString("username");
                String resultPassword = result.getString("password");
                String resultEmail = result.getString("email");
                resultUser = new User(resultUsername, resultPassword, resultEmail);
            } else {
                resultUser = null;
            }
            return resultUser;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    //public User readUser(String username) throws DataAccessException {
    //    for (User user : users) {
    //        if (user.getUsername().compareTo(username) == 0) {
    //            return user;
    //        }
    //    }
    //    return null;
    //}

    public HashSet<User> getUsers() throws DataAccessException {
        //TODO: Merge this code and the code for readUser
        HashSet<User> userList = new HashSet<>();
        try (ResultSet result = executeStatement(getAllUserRows, new ArrayList<>())) {
            while (result.next()) {
                String resultUsername = result.getString("username");
                String resultPassword = result.getString("password");
                String resultEmail = result.getString("email");
                userList.add(new User(resultUsername,
                        resultPassword, resultEmail));
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
        return userList;
    }

    //public HashSet<User> getUsers() throws DataAccessException {
    //    return users;
    //}

    public void removeAllUsers() throws DataAccessException {
        try (ResultSet result = executeStatement(clearUserTable, new ArrayList<>())) {

        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    //public void removeAllUsers() throws DataAccessException {
    //    users = new HashSet<>();
    //}

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
