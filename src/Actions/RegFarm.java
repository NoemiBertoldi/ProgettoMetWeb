package Actions;

import Beans.FarmBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import util.TableReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class RegFarm extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        FarmBean bean = (FarmBean) form;
        ResultSet resultSet;
        String username, password, role, cf, nome, cognome;
        String indirizzo, telefono, nomeF, dataNascita;
        TableReader reader;

        try
        {
            reader = new TableReader();
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

            String query = "SELECT * FROM pharmacies WHERE name = '" + nomeF + "'" + " AND address = '" + indirizzo +
                    "' AND tel = '" + telefono + "'";
            resultSet = reader.getTable(query);
            int count = 0;

            while(resultSet.next())
                count++;

            if(count != 0)
            {
                request.setAttribute("exitCode", "This Pharmacy already exists");
                return mapping.findForward("REGISTER_FAIL");
            }

            query = "SELECT * FROM personnel WHERE username = '" + username + "'";
            resultSet = reader.getTable(query);
            count = 0;

            while(resultSet.next())
                count++;

            if(count != 0)
            {
                request.setAttribute("exitCode", "Staff member alredy exists");
                return mapping.findForward("REGISTER_FAIL");
            }

            query = "INSERT INTO pharmacies (name, address, tel) VALUES ("
                    + "'" + nomeF + "', " + "'" + indirizzo + "', " + "'" + telefono + "')";
            reader.update(query);

            query = "SELECT id FROM pharmacies WHERE name = '" + nomeF + "' AND address = '" + indirizzo + "' AND tel = '" + telefono + "'";
            resultSet = reader.getTable(query);
            int idFarmacia = 0;
            while(resultSet.next())
                idFarmacia = resultSet.getInt("id");


            query = "INSERT INTO personnel (cf, idpharm, role, name, surname, bdate, username, pass) values ("
                    + "'" + cf + "', " + "'" + idFarmacia + "', " + "'" + role + "', " + "'" + nome + "', " + "'" + cognome + "', " + "'" + dataNascita + "', "
                    + "'" + username + "', " + "'" + password + "')";
            reader.update(query);

            int i = 0;
            query = "SELECT codprod FROM products";
            resultSet = reader.getTable(query);

            query = "INSERT INTO warehouse(idpharm, codprod, availqty) VALUES ";
            while(resultSet.next())
            {
                if(i != 0)
                    query += ", ";
                query += "(" + idFarmacia + ", '" + resultSet.getString("codprod") + "', 0)";
                i++;
            }
            reader.update(query);

            request.setAttribute("exitCode", "Successfully Registered");
            return mapping.findForward("REGISTER_OK");
        }
        catch (Exception e)
        {
            System.out.println("Query Error");
            e.printStackTrace();

            request.setAttribute("exitCode", "Invalid SQL query");
            return mapping.findForward("REGISTER_FAIL");
        }
    }
}
