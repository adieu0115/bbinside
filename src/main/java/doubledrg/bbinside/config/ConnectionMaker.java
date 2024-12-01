package doubledrg.bbinside.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMaker
{
    public Connection makeConnection() throws ClassNotFoundException, SQLException
    {
        final String url = "jdbc:mysql://localhost:3306/bbinside";
        final String username = "root";
        final String password = "endyd132!!";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
