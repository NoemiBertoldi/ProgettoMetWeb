package Actions;

import Beans.AnalysisBean;
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

public class AnalisiDate extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        AnalysisBean bean= (AnalysisBean) form;
        Statement statement = null;
        ResultSet resultSet;
        String datai, dataf;
        Connection connection= DbConnection.connect();
        if(connection==null)
        {
            request.setAttribute("exitCode", "No DB connection");
            return mapping.findForward("ERROR");
        }

        try
        {
            statement = connection.createStatement();
            datai=bean.getDatai();
            dataf=bean.getDataf();
            String query= "SELECT * FROM purchases WHERE datep<'"+dataf+"' AND datep>'"+datai+"'";
            resultSet = statement.executeQuery(query);

            int conta = 0;

            while(resultSet.next())
                conta++;

            if(conta > 0)
            {
                request.setAttribute("exitCode", "There is some data");
                request.setAttribute("datai", datai);
                request.setAttribute("dataf", dataf);
                connection.close();
                return mapping.findForward("DATE_OK");
            }
            else
            {
                request.setAttribute("exitCode", "No data found");
                connection.close();
                return mapping.findForward("ERROR");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("exitCode", "Invalid SQL query");
            return mapping.findForward("ERROR");
        }

    }
}
