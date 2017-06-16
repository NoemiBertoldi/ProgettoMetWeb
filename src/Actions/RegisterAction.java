package Actions;

import Beans.LoginBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import util.LoginCheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String result = LoginCheck.check(((LoginBean) form), request, null);

        if(result.equals("LOGIN_OK"))
            return mapping.findForward("LOGIN_OK");
        else
            return mapping.findForward("ERROR");
    }
}
