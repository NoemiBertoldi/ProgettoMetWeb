package Actions;

import Beans.LoginBean;
import Beans.ProdBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import Objects.Acquista;
import util.TableReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Carrello extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ProdBean bean = (ProdBean) form;
        boolean result;
        String query, username, cf = "", codProdotto = "";
        int oldQty = -1, qty = 0, codAcquisto = -1, idFarmacia = -1;
        ResultSet table;
        TableReader reader = new TableReader();
        Acquista acquisto = (Acquista) request.getSession().getAttribute("cart");
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");

        try
        {
            if (acquisto == null)
            {
                username = ((LoginBean) request.getSession().getAttribute("RegisterBean")).getUsername();
                query = "SELECT cf, idFarmacia FROM Operatori WHERE username = '" + username + "'";
                table = reader.getTable(query);

                while (table.next())
                {
                    cf = table.getString("cf");
                    idFarmacia = table.getInt("idFarmacia");
                }

                query = "INSERT INTO acquisti(cfoperatore, totale, data) " +
                        "VALUES ('" + cf + "', 0, '" + sf.format(date) + "')";
                reader.update(query);

                acquisto = new Acquista(cf, date, idFarmacia);
                request.getSession().setAttribute("cart", acquisto);
            }

            else
            {
                cf = acquisto.getCfOp();
                date = acquisto.getDate();
                idFarmacia = acquisto.getIdFarmacia();
            }

            //recupera il codice dell'acquisto corrente
            query = "SELECT codAcquisto FROM acquisti WHERE cfOperatore = '" + cf + "' AND totale = 0 AND data = '" + sf.format(date) + "'";
            table = reader.getTable(query);

            while (table.next())
                codAcquisto = table.getInt("codAcquisto");

            codProdotto = bean.getProductName();
            qty = Integer.parseInt(bean.getQty());
            query = "SELECT quantitadisponibile FROM magazzino WHERE idFarmacia = " + idFarmacia +
                    " AND codProdotto = '" + codProdotto + "'";
            table = reader.getTable(query);

            while (table.next())
                oldQty = table.getInt("quantitadisponibile");

            if(oldQty < qty)
            {
                request.getSession().setAttribute("msg", "DISPONIBILITÀ SUPERATA!");
                return mapping.findForward("ADD_OK");
            }

            query = "INSERT INTO carrello (codprodotto, quantita, codacquisto)" +
                    " VALUES ('" + codProdotto + "', " + qty + ", '" + codAcquisto + "');";
            reader.update(query);

            query = "UPDATE magazzino SET quantitadisponibile = " + (oldQty - qty) +
                    " WHERE idFarmacia = " + idFarmacia + " AND codProdotto = '" + codProdotto + "'";
            reader.update(query);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            request.getSession().setAttribute("msg", "ERRORE");
            return mapping.findForward("ERROR");
        }

        request.getSession().setAttribute("msg", "PRODOTTO AGGIUNTO AL CARRELLO");
        return mapping.findForward("ADD_OK");
    }
}
