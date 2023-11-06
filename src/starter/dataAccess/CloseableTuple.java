package dataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CloseableTuple implements AutoCloseable {
    private ResultSet result;
    private Connection conn;

    public CloseableTuple(ResultSet result, Connection conn) {
        this.result = result;
        this.conn = conn;
    }

    public void close() {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException ex) {
                System.out.println("result.close() threw and exception!");
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("conn.close() threw and exception!");
            }
        }
    }

    public ResultSet getResult() {
        return result;
    }

    public Connection getConn() {
        return conn;
    }
}
