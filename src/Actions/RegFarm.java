package Actions;

import util.DbConnection;
import Beans.FarmBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegFarm extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        FarmBean bean = (FarmBean) form;
        Statement statement = null;
        ResultSet resultSet;
        String username, password, role, cf, nome, cognome;
        String indirizzo = "", telefono = "", nomeF = "", dataNascita = "";

        Connection connection=DbConnection.connect();
        if(connection==null)
        {
            request.setAttribute("exitCode", "No DB connection");
            return mapping.findForward("REGISTER_FAIL");
        }

        try
        {
            statement = connection.createStatement();
            username = bean.getUsername();
            password = bean.getPassword();
            role = "TF";
            cf = bean.getCf();
            nome = bean.getNome();
            cognome = bean.getCognome();
            dataNascita = bean.getDataNascita();
            nomeF = bean.getNomeF();
            indirizzo = bean.getIndirizzo();
            telefono = bean.getTelefono();

            String query = "SELECT * FROM Farmacie WHERE nome = '" + nomeF + "'" + " AND indirizzo = '" + indirizzo +
                    "' AND telefono = '" + telefono + "'";
            resultSet = statement.executeQuery(query);
            int conta = 0;

            while(resultSet.next())
                conta++;

            if(conta != 0)
            {
                request.setAttribute("exitCode", "This Pharmacy already exists");
                return mapping.findForward("REGISTER");
            }

            query= "SELECT * FROM operatori WHERE username='"+username+"'";
            resultSet= statement.executeQuery(query);
            conta=0;
            while(resultSet.next())
                conta++;
            if(conta!=0)
            {
                request.setAttribute("exitCode", "Staff member alredy exists");
                return mapping.findForward("REGISTER_OK");
            }

            query = "INSERT INTO Farmacie (nome, indirizzo, telefono) VALUES ("
                    + "'" + nomeF + "', " + "'" + indirizzo + "', " + "'" + telefono + "')";
            statement.executeUpdate(query);

            query = "SELECT id FROM Farmacie WHERE nome = '" + nomeF + "' AND indirizzo = '" + indirizzo +
                    "' AND telefono = '" + telefono + "'";

            resultSet = statement.executeQuery(query);
            int idFarmacia = 0;

            while(resultSet.next())
                idFarmacia = resultSet.getInt("id");

            query = "INSERT INTO Operatori (cf, idFarmacia, ruolo, nome, cognome, dataNascita,  username, password) " +
                    "values ("
                    + "'" + cf + "', " + "'" + idFarmacia + "', " + "'" + role + "', " + "'" + nome + "', " +
                    "'" + cognome + "', " + "'" + dataNascita + "', "+
                    "'" + username + "', " + "'" + password + "')";
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
