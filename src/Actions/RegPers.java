package Actions;

import Beans.LoginBean;
import Beans.PersBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import util.DbConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegPers extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PersBean bean = (PersBean) form;
        Statement statement = null;
        ResultSet resultSet;
        String username, password, role, cf, nome, cognome;
        String dataNascita;

        Connection connection= DbConnection.connect();
        if(connection==null)
        {
            request.setAttribute("exitCode", "No DB connection");
            return mapping.findForward("REGISTER_FAIL");
        }

        try
        {
            statement = connection.createStatement();
            username = bean.getUsername();

            String query = "SELECT * FROM personnel WHERE username = '" + username + "'";
            resultSet = statement.executeQuery(query);
            int conta = 0;

            while(resultSet.next())
                conta++;

            if(conta != 0)
            {
                request.setAttribute("exitCode", "Username already exists");
                return mapping.findForward("REGISTER_FAIL");
            }

            password = bean.getPassword();
            role = bean.getRole();
            cf = bean.getCf();
            nome = bean.getNome();
            cognome = bean.getCognome();
            dataNascita = bean.getDataNascita();

            String username_tit = ((LoginBean) request.getSession().getAttribute("LoginBean")).getUsername();
            query = "SELECT * FROM personnel WHERE username = '" + username_tit + "'";
            resultSet = statement.executeQuery(query);

            int idFarmacia = 0;
            while(resultSet.next())
                idFarmacia = resultSet.getInt("idpharm");

            query = "INSERT INTO personnel (cf, idpharm, role, name, surname, bdate, username, pass) values ("
                    + "'" + cf + "', " + idFarmacia + ", " + "'" + role + "', " + "'" + nome + "', " + "'" + cognome + "', " + "'" + dataNascita + "', "
                    + "'" + username + "', " + "'" + password + "')";
            statement.executeUpdate(query);

            request.setAttribute("exitCode", "Successfully Registered");
            return mapping.findForward("REGISTER_OK");
        }
        catch (Exception e)
        {
            System.out.println("Query Error");
            e.printStackTrace();
            connection.close();

            request.setAttribute("exitCode", "Invalid SQL query");
            return mapping.findForward("REGISTER_FAIL");
        }
    }
}
