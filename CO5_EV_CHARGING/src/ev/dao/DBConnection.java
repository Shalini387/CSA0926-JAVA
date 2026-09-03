package ev.dao;
import java.sql.*;
public class DBConnection {
    private static final String URL="jdbc:mysql://localhost:3306/ev_charging_db?useSSL=false&serverTimezone=UTC";
    private static final String USER="root", PASS="root";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASS);
    }
}
