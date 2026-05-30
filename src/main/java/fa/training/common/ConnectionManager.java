package fa.training.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    static {
        try{
            Class.forName(PropertyManager.getProperty("db.driver"));
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertyManager.getProperty("db.url"),
                PropertyManager.getProperty("db.username"),
                PropertyManager.getProperty("db.password")
        );
    }
}
