package Actions;

import Beans.LoginBean;
import Beans.ProdBean;
import Beans.PresBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import Objects.CodAcquisto;
import Objects.QtaIniziale;
import Objects.Acquista;
import Objects.Prescription;
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
        PresBean presBean;
        ProdBean prodBean;
        boolean needsPres = true;
        String query, username, cf="", codProdotto="", role, purchaseDate;
        Prescription prescription;
        int oldQty = -1, qty = 0, codAcquisto = -1, idFarmacia = -1, cartId=-1;
        ResultSet table;
        TableReader reader = new TableReader();
        Acquista purch = (Acquista) request.getSession().getAttribute("cart");
        Date date = new Date();
        SimpleDateFormat PrescriptionDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat todaysDateFormatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
        float total = 0;

        try
        {
            prescription = (Prescription) request.getSession().getAttribute("ricetta");

            if(prescription == null)
            {
                prodBean = (ProdBean) form;
                codProdotto = prodBean.getProductName();
                try
                {
                    qty=Integer.parseInt(prodBean.getQty());
                }
                catch (Exception e)
                {
                    request.getSession().setAttribute("msg","Invalid quantity");
                    return mapping.findForward("ERROR");
                }
                query = "SELECT needspres FROM products WHERE codprod = '" + codProdotto + "'";
                table = reader.getTable(query);

                while (table.next())
                    needsPres = table.getBoolean("needspres");

                role = (String) request.getSession().getAttribute("role");

                if(needsPres)
                {
                    if (role.toLowerCase().equals("ob"))
                    {
                        request.getSession().setAttribute("msg", "Bench operator can't sell a drug that needs prescription");
                        return mapping.findForward("ERROR");
                    }

                    else
                    {
                        request.getSession().setAttribute("ricetta", new Prescription(codProdotto, qty));
                        return mapping.findForward("NEED_PRES");
                    }
                }
            }
            else
            {
                codProdotto = prescription.getCodProdotto();
                qty = prescription.getQty();
            }

            if (purch == null)
            {
                username = ((LoginBean) request.getSession().getAttribute("LoginBean")).getUsername();
                query = "SELECT cf, idpharm FROM personnel WHERE username = '" + username + "'";
                table = reader.getTable(query);

                while (table.next())
                {
                    cf = table.getString("cf");
                    idFarmacia = table.getInt("idpharm");
                }

                purchaseDate = todaysDateFormatter.format(date);
                query = "INSERT INTO purchases(cfpers, total, datep, completed) " +
                        "VALUES ('" + cf + "', 0, '" + purchaseDate + "', false)";
                reader.update(query);

                purch = new Acquista(cf, purchaseDate, idFarmacia);
                request.getSession().setAttribute("cart", purch);
            }
            else
            {
                cf = purch.getCfOp();
                purchaseDate = purch.getFormatDate();
                idFarmacia = purch.getIdFarmacia();
            }

            query = "SELECT codpurch FROM purchases WHERE cfpers = '" + cf + "' AND datep = '" + purchaseDate + "'";
            table = reader.getTable(query);

            while (table.next())
                codAcquisto = table.getInt("codpurch");

            request.getSession().setAttribute("codAcquisto", new CodAcquisto(codAcquisto));

            if(request.getSession().getAttribute("quantity") == null)
            {
                query = "SELECT availqty FROM warehouse WHERE idpharm = " + idFarmacia +
                        " AND codprod = '" + codProdotto + "'";
                table = reader.getTable(query);

                while (table.next())
                    oldQty = table.getInt("availqty");

                request.getSession().setAttribute("quantity", new QtaIniziale(oldQty));
            }
            else
            {
                oldQty = ((QtaIniziale) request.getSession().getAttribute("quantity")).getQty();
            }

            if(oldQty < qty)
            {
                request.getSession().setAttribute("msg", "You selected too many products!");
                return mapping.findForward("ERROR");
            }

            query = "INSERT INTO carrello (codprodotto, quantita, codacquisto)" +
                    " VALUES ('" + codProdotto + "', " + qty + ", " + codAcquisto + ");";
            reader.update(query);

            query = "SELECT id FROM cart WHERE codprod = '" + codProdotto + "' AND"
                    + " qty = " + qty + " AND codpurch = " + codAcquisto;
            table = reader.getTable(query);
            while (table.next())
                cartId = table.getInt("id");

            query = "SELECT price FROM products WHERE codprod = '" + codProdotto + "'";
            table = reader.getTable(query);
            while (table.next())
                total = table.getFloat("price");
            total = total * qty;

            query = "UPDATE purchases SET total = " + total + " WHERE codpurch = '" + codAcquisto + "'";
            reader.update(query);

            if(prescription != null)
            {
                presBean = (PresBean) form;
                String cfPaz, nome, cognome, dataNascita, codReg;
                int count = 0;

                cfPaz = presBean.getCfPaz();
                nome = presBean.getNomePaz();
                cognome = presBean.getCognomePaz();
                dataNascita = presBean.getDataNascitaPaz();
                codReg = presBean.getCodRegMed();

                query = "INSERT INTO prescriptions(codpres, idcart, codreg, datepr)"+
                        " VALUES ('" + (codAcquisto + "," + codReg +
                        PrescriptionDateFormatter.format(date)) +"', '" + cartId +"', '" + codReg + "', '" + todaysDateFormatter.format(date) + "')";

                if(! reader.update(query))
                {
                    request.getSession().setAttribute("msg", "Doctor Not Found! ");
                    revertChanges(request, cf, idFarmacia, codProdotto, codAcquisto, oldQty);
                    return mapping.findForward("ERROR");
                }

                query = "SELECT * FROM patients WHERE cf = '" + cfPaz + "'";
                table = reader.getTable(query);
                while (table.next())
                    count++;

                if(count == 0)
                {
                    query = "INSERT INTO patients(cf, codpurch, name, surname, bdate)" +
                            " VALUES ('" + cfPaz + "', " + codAcquisto + ", '" + nome + "', '" + cognome + "', '" + dataNascita + "')";
                    reader.update(query);
                }

                request.getSession().removeAttribute("ricetta");
            }

            query = "UPDATE warehouse SET availqty = " + (oldQty - qty) +
                    " WHERE idpharm = " + idFarmacia + " AND codprod = '" + codProdotto + "'";
            reader.update(query);

        }
        catch(Exception e)
        {
            e.printStackTrace();

            revertChanges(request, cf, idFarmacia, codProdotto, codAcquisto, oldQty);

            request.getSession().setAttribute("exitCode", "Add To Cart Error");
            return mapping.findForward("ERROR");
        }

        request.getSession().setAttribute("msg", "PRODOTTO AGGIUNTO AL CARRELLO");
        return mapping.findForward("ADD_OK");
    }

    private static void revertChanges(HttpServletRequest request, String cf, int idFarmacia, String codProdotto, int codAcquisto, int oldQty)
    {
        String query;
        TableReader reader;

        try
        {
            reader = new TableReader();
            if (oldQty != -1)
            {
                query = "UPDATE warehouse SET availqty = " + oldQty +
                        " WHERE idpharm = " + idFarmacia + " AND codprod = '" + codProdotto + "'";
                reader.update(query);
            }
            query = "DELETE FROM cart WHERE codpurch = " + codAcquisto;
            reader.update(query);

            if(request.getSession().getAttribute("ricetta") == null)
            {
                query = "DELETE FROM purchases WHERE cfpers = '" + cf + "'";
                reader.update(query);
            }

            request.getSession().removeAttribute("ricetta");
            request.getSession().removeAttribute("cart");
            request.getSession().removeAttribute("quantity");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
