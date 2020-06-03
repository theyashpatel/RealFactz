package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final String LINK = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "realfactz";

    public static final String URL = "jdbc:mysql://" + LINK + ":" + PORT + "/" + DB_NAME;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root123";

    private static Connection connection = null;

    public Connection connect() {
        try {
            if (connection == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection( URL, USERNAME, PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
