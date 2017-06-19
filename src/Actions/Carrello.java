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
        PresBean recBean;
        ProdBean prodBean;
        boolean conRicetta = true;
        String query, username, cf = "", codProdotto = "", role = "", acquistoDate = "";
        Prescription ricetta;
        int oldQty = -1, qty = 0, codAcquisto = -1, idFarmacia = -1;
        ResultSet table;
        TableReader reader = new TableReader();
        Acquista acquisto = (Acquista) request.getSession().getAttribute("cart");
        Date date = new Date();
        SimpleDateFormat PrescriptionDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat todaysDateFormatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
        float total = 0;

        try
        {
            ricetta = (Prescription) request.getSession().getAttribute("ricetta");

            if(ricetta == null)
            {
                prodBean = (ProdBean) form;
                codProdotto = prodBean.getProductName();
                /*per ora non va*/
                try
                {
                    qty = Integer.parseInt(prodBean.getQty());
                }
                catch(Exception e)
                {
                    request.getSession().setAttribute("msg", "QUANTITA' INSERITA NON CORRETTA");
                    return mapping.findForward("ADD_OK");
                }
                query = "SELECT conRicetta FROM Prodotti WHERE codProdotto = '" + codProdotto + "'";
                table = reader.getTable(query);

                while (table.next())
                    conRicetta = table.getBoolean("conRicetta");

                role = (String) request.getSession().getAttribute("role");

                if(conRicetta)
                {
                    if (role.toLowerCase().equals("ob"))
                    {
                        request.getSession().setAttribute("msg", "OPERATORE DI BANCO NON PUO' VENDERE UN PRODOTTO CON RICETTA!");
                        return mapping.findForward("ADD_OK");
                    }

                    else
                    {
                        request.getSession().setAttribute("ricetta", new Prescription(codProdotto, qty));
                        return mapping.findForward("NEED_Prescription");
                    }
                }
            }
            else
            {
                recBean = (PresBean) form;
                codProdotto = ricetta.getCodProdotto();
                qty = ricetta.getQty();
            }

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

                acquistoDate = todaysDateFormatter.format(date);
                query = "INSERT INTO acquisti(cfoperatore, totale, data, completato) " +
                        "VALUES ('" + cf + "', 0, '" + acquistoDate + "', false)";
                reader.update(query);

                acquisto = new Acquista(cf, acquistoDate, idFarmacia);
                request.getSession().setAttribute("cart", acquisto);
            }
            else
            {
                cf = acquisto.getCfOp();
                acquistoDate = acquisto.getFormatDate();
                idFarmacia = acquisto.getIdFarmacia();
            }

            query = "SELECT codAcquisto FROM acquisti WHERE cfOperatore = '" + cf + "' AND data = '" + acquistoDate + "'";
            table = reader.getTable(query);

            while (table.next())
                codAcquisto = table.getInt("codAcquisto");

            request.getSession().setAttribute("codAcquisto", new CodAcquisto(codAcquisto));

            if(request.getSession().getAttribute("quantity") == null)
            {
                query = "SELECT quantitadisponibile FROM magazzino WHERE idFarmacia = " + idFarmacia +
                        " AND codProdotto = '" + codProdotto + "'";
                table = reader.getTable(query);

                while (table.next())
                    oldQty = table.getInt("quantitadisponibile");

                request.getSession().setAttribute("quantity", new QtaIniziale(oldQty));
            }
            else
            {
                oldQty = ((QtaIniziale) request.getSession().getAttribute("quantity")).getQty();
            }

            if(oldQty < qty)
            {
                request.getSession().setAttribute("msg", "DISPONIBILITÀ SUPERATA!");
                return mapping.findForward("ADD_OK");
            }

            query = "INSERT INTO carrello (codprodotto, quantita, codacquisto)" +
                    " VALUES ('" + codProdotto + "', " + qty + ", " + codAcquisto + ");";
            reader.update(query);

            query = "SELECT prezzo FROM Prodotti WHERE codProdotto = '" + codProdotto + "'";
            table = reader.getTable(query);
            while (table.next())
                total = table.getFloat("prezzo");
            total = total * qty;

            query = "UPDATE Acquisti SET totale = " + total + " WHERE codAcquisto = '" + codAcquisto + "'";
            reader.update(query);

            if(ricetta != null)
            {
                recBean = (PresBean) form;
                String cfPaz, nome, cognome, dataNascita, codReg;
                int conta = 0;

                cfPaz = recBean.getCfPaz();
                nome = recBean.getNomePaz();
                cognome = recBean.getCognomePaz();
                dataNascita = recBean.getDataNascitaPaz();
                codReg = recBean.getCodRegMed();

                query = "INSERT INTO ricette(codricetta, codacquisto, codregionale, data)"+
                        " VALUES ('" + (codAcquisto + "," + codReg + PrescriptionDateFormatter.format(date)) +"', '" + codAcquisto +"', '" + codReg + "', '" + todaysDateFormatter.format(date) + "')";

                if(! reader.update(query))
                {
                    request.getSession().setAttribute("msg", "MEDICO NON TROVATO! ");
                    revertChanges(request, cf, idFarmacia, codProdotto, codAcquisto, oldQty);
                    return mapping.findForward("ADD_OK");
                }

                query = "SELECT * FROM Pazienti WHERE cf = '" + cfPaz + "'";
                table = reader.getTable(query);
                while (table.next())
                    conta++;

                if(conta == 0)
                {
                    query = "INSERT INTO pazienti(cf, codacquisto, nome, cognome, datanascita)" +
                            " VALUES ('" + cfPaz + "', " + codAcquisto + ", '" + nome + "', '" + cognome + "', '" + dataNascita + "')";
                    reader.update(query);
                }

                request.getSession().removeAttribute("ricetta");
            }

            query = "UPDATE magazzino SET quantitadisponibile = " + (oldQty - qty) +
                    " WHERE idFarmacia = " + idFarmacia + " AND codProdotto = '" + codProdotto + "'";
            reader.update(query);

        }
        catch(Exception e)
        {
            e.printStackTrace();

            revertChanges(request, cf, idFarmacia, codProdotto, codAcquisto, oldQty);

            request.getSession().setAttribute("exitCode", "ERRORE AGGIUNTA AL CARRELLO");
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
                query = "UPDATE magazzino SET quantitadisponibile = " + oldQty +
                        " WHERE idFarmacia = " + idFarmacia + " AND codProdotto = '" + codProdotto + "'";
                reader.update(query);
            }
            query = "DELETE FROM Carrello WHERE codAcquisto = " + codAcquisto;
            reader.update(query);

            if(request.getSession().getAttribute("ricetta") == null)
            {
                query = "DELETE FROM Acquisti WHERE cfOperatore = '" + cf + "'";
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
