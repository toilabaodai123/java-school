package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String url= "jdbc:mysql://localhost:3306/school";
        String user = "school";
        String password = "school";

        try (Connection conn = DriverManager.getConnection(url, user, password){
            return conn;
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
