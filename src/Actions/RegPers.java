package Actions;

import Beans.PersBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegPers extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PersBean bean = (PersBean) form;
        HttpSession session;
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet;
        String username = "", password = "", role = "", cf = "", nome = "", cognome = "", codReg = "";
        String indirizzo = "", telefono = "", nomeF = "", dataNascita = "";

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

            request.setAttribute("exitCode", "Errore Connessione al DB");
            return mapping.findForward("REGISTER");
        }

        try
        {
            st = connection.createStatement();
            username = bean.getUsername();

            //prima cerca se username esiste già
            String query = "SELECT * FROM Operatori WHERE username = '" + username + "'";
            resultSet = st.executeQuery(query);
            int conta = 0;

            while(resultSet.next())
                conta++;

            if(conta != 0)
            {
                request.setAttribute("exitCode", "Username già esistente");
                return mapping.findForward("REGISTER");
            }

            password = bean.getPassword();
            role = bean.getRole();
            cf = bean.getCf();
            nome = bean.getNome();
            cognome = bean.getCognome();
            codReg = bean.getCodRegionale();
            dataNascita = bean.getDataNascita();

            query = "INSERT INTO Operatori (cf, ruolo, nome, cognome, dataNascita, codRegionale, username, pass) values ("
                    + "'" + cf + "', " + "'" + role + "', " + "'" + nome + "', " + "'" + cognome + "', " + "'" + dataNascita + "', "
                    + "'" + codReg + "', " + "'" + username + "', " + "'" + password + "')";
            st.executeUpdate(query);

            request.setAttribute("exitCode", "REGISTRAZIONE AVVENUTA CON SUCCESSO");
            return mapping.findForward("REGISTER");
        }
        catch (Exception e)
        {
            System.out.println("Errore nella query");
            e.printStackTrace();
            connection.close();

            request.setAttribute("exitCode", "Query sql non valida");
            return mapping.findForward("REGISTER");
        }
    }
}
