package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Database is responsible for creating connections to the database. Connections are
 * managed with a simple pool in order to increase performance. To obtain and
 * use connections represented by this class use the following pattern.
 *
 * <pre>
 *  public boolean example(String selectStatement, Database db) throws DataAccessException{
 *    var conn = db.getConnection();
 *    try (var preparedStatement = conn.prepareStatement(selectStatement)) {
 *        return preparedStatement.execute();
 *    } catch (SQLException ex) {
 *        throw new DataAccessException(ex.toString());
 *    } finally {
 *        db.returnConnection(conn);
 *    }
 *  }
 * </pre>
 */
public class Database {

    // FIXME: Change these fields, if necessary, to match your database configuration
    public static final String DB_NAME = "chess";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "MyPassword";

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306";

    private final LinkedList<Connection> connections = new LinkedList<>();

    /**
     * Get a connection to the database. This pulls a connection out of a simple
     * pool implementation. The connection must be returned to the pool after
     * you are done with it by calling {@link #returnConnection(Connection) returnConnection}.
     *
     * @return Connection
     */
    synchronized public Connection getConnection() throws DataAccessException {
        try {
            Connection connection;
            if (connections.isEmpty()) {
                connection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
                connection.setCatalog(DB_NAME);
            } else {
                connection = connections.removeFirst();
            }
            return connection;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Return a previously acquired connection to the pool.
     *
     * @param connection previous obtained by calling {@link #getConnection() getConnection}.
     */
    synchronized public void returnConnection(Connection connection) {
        connections.add(connection);
    }

    public void initDatabase() {
        UserDAO userDAOObj = new UserDAO();
        AuthDAO authDAOObj = new AuthDAO();
        GameDAO gameDAOObj = new GameDAO();

        try (Connection conn = DriverManager.getConnection(
                CONNECTION_URL, DB_USERNAME, DB_PASSWORD)) {
            //TODO: Specify auto increment variables and null values

            // Create the  database
            PreparedStatement createDbStatement = conn.prepareStatement(
                    "CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();
            conn.setCatalog("chess");

            // Create tables
            //TODO: These don't need to take in a connection as input
            userDAOObj.initUserTable();
            authDAOObj.initAuthTable(conn);
            gameDAOObj.initGameTable(conn);

        } catch (SQLException | DataAccessException ex) {
            System.out.println(String.format("Exception occurred: %s", ex));
        }
    }
}

