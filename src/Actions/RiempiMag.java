package Actions;
import Beans.LoginBean;
import Beans.MagBean;
import org.apache.struts.action.*;
import util.TableReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class RiempiMag extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MagBean bean = (MagBean) form;
        boolean res;
        String query, nomeprodotto, username, codprod="";
        int qty = -1, farm = -1, oldQty=0;
        ResultSet table;

        TableReader reader = new TableReader();
        nomeprodotto = bean.getNomeprodotto();
        qty= Integer.parseInt(bean.getQty());
        username= ((LoginBean) request.getSession().getAttribute("LoginBean")).getUsername();

        query = "SELECT idpharm FROM personnel WHERE username ='"+username+"'";
        table = reader.getTable(query);
        while(table.next())
            farm=table.getInt("idpharm");

        query="SELECT codprod FROM products WHERE name ='" + nomeprodotto+"'";
        table=reader.getTable(query);
        while (table.next())
            codprod=table.getString("codprod");

        query="SELECT availqty FROM warehouse WHERE codprod='"+codprod+"' AND idpharm = "+farm;
        table = reader.getTable(query);
        while(table.next())
            oldQty=table.getInt("availqty");

        query="UPDATE warehouse SET availqty="+(oldQty+qty)+"WHERE codprod='"+codprod+"' AND idpharm="+farm;
        res=reader.update(query);

        if (res)
            return mapping.findForward("RIEMPI_OK");
        else
        {
            request.setAttribute("exitCode","Order Error");
            return mapping.findForward("ERROR");
        }
    }
}
