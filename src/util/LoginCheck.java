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
        Statement st = null;
        ResultSet resultSet;
        String username, password, role;
        boolean loginOk = false;

        Connection connection=DbConnection.connect();
        if(connection==null)
        {
            return "No DB connection";
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
                tableName = "region";
            }
            else
            {
                tableName = "personnel";
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
                        role = resultSet.getString("role");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("query error");
            e.printStackTrace();
            connection.close();

            return "invalid SQL Query";
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
                    request.getSession().setAttribute("exitCode", "This area is reserved to: " + roleCheck);
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
            request.getSession().setAttribute("exitCode", "Username or Password are not correct");
            connection.close();
            return "ERROR";
        }
    }
}

