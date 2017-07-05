package Actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import Objects.CodAcquisto;
import util.TableReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class Acquisto extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        TableReader reader;
        String query;
        ResultSet table;
        boolean completato = true, ok = false;

        try
        {
            reader = new TableReader();

            int codAcquisto = ((CodAcquisto) request.getSession().getAttribute("codAcquisto")).getCodAcquisto();

            query = "SELECT completed FROM purchases WHERE codpurch = " + codAcquisto;
            table = reader.getTable(query);
            while (table.next())
                completato = table.getBoolean("completed");

            if(completato)
            {
                request.getSession().setAttribute("exitCode", "Checkout Error : already purchased");
                return mapping.findForward("ERROR");
            }

            query = "UPDATE purchases SET completed = true WHERE codpurch = " + codAcquisto;
            ok = reader.update(query);

            if(! ok)
            {
                request.getSession().setAttribute("exitCode", "Checkout Error");
                return mapping.findForward("ERROR");
            }

            request.getSession().removeAttribute("ricetta");
            request.getSession().removeAttribute("cart");
            request.getSession().removeAttribute("quantity");
            request.getSession().removeAttribute("codAcquisto");
        }
        catch(Exception e)
        {
            request.getSession().setAttribute("exitCode", "Checkout Error");
            return mapping.findForward("ERROR");
        }

        request.getSession().setAttribute("msg", "Successfully Purchased");
        return mapping.findForward(("PURCHASE_OK"));
    }
}
