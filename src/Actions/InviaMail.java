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
        String obj, msg, data, query = "", username, role, dest;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TableReader reader = new TableReader();

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
                   query = "INSERT INTO messaggi(dt_invio, fromreg, toreg, fromop, toop, oggetto, msg)\n" +
                            "    VALUES ('" + data + "', '" + username + "', null, null, '" + dest + "', '" + obj + "', '" + msg + "')";

                }
                else
                {
                    if(dest.toLowerCase().startsWith("reg"))
                        query = "INSERT INTO messaggi(dt_invio, fromreg, toreg, fromop, toop, oggetto, msg)\n" +
                                "    VALUES ('" + data + "', null, '" + dest + "', '" + username + "', null, '" + obj + "', '" + msg + "')";

                    else
                        query = "INSERT INTO messaggi(dt_invio, fromreg, toreg, fromop, toop, oggetto, msg)\n" +
                                "    VALUES ('" + data + "', null, null, '" + username + "', '" + dest + "', '" + obj + "', '" + msg + "')";
                }

                for(int i=1; i<dests.length; i++)
                {
                    dest = dests[i];
                    if(role.toLowerCase().equals("reg"))
                    {
                        query += ", ('" + data + "', '" + username + "', null, null, '" + dest + "', '" + obj + "', '" + msg + "')";
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
            reader.update(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            request.getSession().setAttribute("exitCode", "Mail Error ");
            return mapping.findForward("ERROR");
        }

        request.getSession().setAttribute("msg", "Mail Sent! ");
        return mapping.findForward("SEND_OK");
    }

}
