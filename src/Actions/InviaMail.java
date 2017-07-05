package Actions;

import Beans.LoginBean;
import Beans.MailBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import util.TableReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InviaMail extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MailBean bean = (MailBean) form;
        LoginBean login = (LoginBean) request.getSession().getAttribute("LoginBean");
        String[] dests;
        String obj, msg, data, query = "", username, role, dest, tf = "", queryTf;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TableReader reader = new TableReader();
        ResultSet table;

        try
        {
            role = (String)request.getSession().getAttribute("role");
            username = login.getUsername();
            dests = bean.getUsername();
            obj = bean.getObj();
            msg = bean.getText();
            data = dateFormat.format(new Date());
            if(dests.length > 0)
            {
                dest = dests[0];

                if(role.toLowerCase().equals("reg"))
                {
                    queryTf = "SELECT personnel.username FROM personnel JOIN pharmacies"
                            + " ON personnel.idpharm = pharmacies.id"
                            + " WHERE personnel.role = 'TF' AND pharmacies.name = '" + dest + "'";
                    table = reader.getTable(queryTf);

                    while(table.next())
                        tf = table.getString("username");

                    query = "INSERT INTO messages(datesent, fromreg, toreg, fromop, toop, obj, msg)\n" +
                            "    VALUES ('" + data + "', '" + username + "', null, null, '" + tf + "', '" + obj + "', '" + msg + "')";

                }
                else
                {
                    if(dest.toLowerCase().startsWith("reg"))
                        query = "INSERT INTO messages(datesent, fromreg, toreg, fromop, toop, obj, msg)\n" +
                                "    VALUES ('" + data + "', null, '" + dest + "', '" + username + "', null, '" + obj + "', '" + msg + "')";
                    else
                        query = "INSERT INTO messages(datesent, fromreg, toreg, fromop, toop, obj, msg)\n" +
                                "    VALUES ('" + data + "', null, null, '" + username + "', '" + dest + "', '" + obj + "', '" + msg + "')";
                }

                for(int i=1; i<dests.length; i++)
                {
                    dest = dests[i];
                    if(role.toLowerCase().equals("reg"))
                    {
                        queryTf = "SELECT personnel.username FROM personnel JOIN pharmacies"
                                + " ON personnel.idpharm = pharmacies.id"
                                + " WHERE personnel.role = 'TF' AND pharmacies.name = '" + dest + "'";
                        table = reader.getTable(queryTf);

                        while(table.next())
                            tf = table.getString("username");


                        query += ", ('" + data + "', '" + username + "', null, null, '" + tf + "', '" + obj + "', '" + msg + "')";
                    }
                    else
                    {
                        if(dest.toLowerCase().startsWith("reg"))
                            query += ", ('" + data + "', null, '" + dest + "', '" + username + "', null, '" + obj + "', '" + msg + "')";

                        else
                            query += ", ('" + data + "', null, null, '" + username + "', '" + dest + "', '" + obj + "', '" + msg + "')";
                    }
                }
            }
            else
            {
                request.getSession().setAttribute("msg", "No receiver selected! ");
                return mapping.findForward("ERROR");
            }


            reader.update(query);
        }
        catch(Exception e)
        {
            request.getSession().setAttribute("exitCode", "Mail error ");
            return mapping.findForward("ERROR");
        }

        request.getSession().setAttribute("msg", "Mail sent! ");
        return mapping.findForward("SEND_OK");
    }

}
