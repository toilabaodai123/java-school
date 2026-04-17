package database;

import app.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        var config = Main.getConfig();
        String url = config.get("db.url");
        String user = config.get("db.user");
        String password = config.get("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
