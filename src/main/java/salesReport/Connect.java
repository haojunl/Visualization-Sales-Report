package salesReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String DATABASE_URL = "jdbc:sqlite:chinook.db";

    public static Connection connect() throws RuntimeException{
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
            throw new RuntimeException(e);
        }
        assert conn !=null;
        return conn;
    }
}
