package util;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection
{
    public static Connection connect() throws SQLException {
        Connection connection = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MetWeb_tab", "fedora", "fedora");
        }
        catch (Exception e)
        {
            System.out.println("No DB connection");
            e.printStackTrace();
            connection.close();
            return null;

        }
        return connection;
    }
}
