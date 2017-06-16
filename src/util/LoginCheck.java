package util;

import Beans.LoginBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class LoginCheck
{
    public static String check(LoginBean form, HttpServletRequest request, String roleCheck) throws SQLException
    {
        LoginBean bean = (LoginBean) form;
        HttpSession session;
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet;
        String username = "", password = "", role = "";
        boolean loginOk = false;

        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MetWeb_tab", "fedora", "fedora");
        }
        catch (Exception e)
        {
            System.out.println("Errore connessione al DB");
            e.printStackTrace();
            connection.close();

            return "Errore Connessione al DB";
        }

        try
        {
            if(bean == null)
                bean = (LoginBean) request.getSession().getAttribute("LoginBean");
            st = connection.createStatement();
            username = bean.getUsername();
            password = bean.getPasswd();
            role = bean.getRole();
            String tableName;

            if(role.equals("reg"))
            {
                tableName = "regione";
            }
            else
            {
                tableName = "operatori";
            }

            resultSet = st.executeQuery("SELECT * FROM " + tableName + " WHERE username = '" + username
                    + "' AND pass = '" + password + "'");


            while(resultSet.next())
            {
                String dbUsername = resultSet.getString("username");
                String db_pass = resultSet.getString("pass");

                if(dbUsername.equals(username) && (db_pass.equals(password)))
                {
                    loginOk = true;

                    if(! role.equals("reg"))
                        role = resultSet.getString("ruolo");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Errore nella query");
            e.printStackTrace();
            connection.close();

            return "Query sql non valida";
        }

        if(loginOk)
        {
            if(roleCheck != null)
            {

                if(roleCheck.toLowerCase().equals("pers") && (role.toLowerCase().equals("tf") || role.toLowerCase().equals("df") || role.toLowerCase().equals("ob")))
                {

                    request.getSession().setAttribute("role", role);
                    connection.close();
                    return "LOGIN_OK";
                }

                else if(roleCheck.toLowerCase().equals(role.toLowerCase()))
                {

                    request.getSession().setAttribute("role", role);
                    connection.close();
                    return "LOGIN_OK";
                }
                else
                {
                    request.setAttribute("exitCode", "Area riservata a " + roleCheck);
                    return "ERROR";
                }
            }

            else
            {

                request.getSession().setAttribute("role", role);
                connection.close();
                return "LOGIN_OK";
            }
        }
        else
        {
            request.setAttribute("exitCode", "Username o Password non corretti");
            connection.close();
            return "ERROR";
        }
    }
}

