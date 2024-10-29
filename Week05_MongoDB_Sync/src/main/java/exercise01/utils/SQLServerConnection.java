package exercise01.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLServerConnection {
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=BikeStores";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";

    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
