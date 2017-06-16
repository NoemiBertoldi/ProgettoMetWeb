package Beans;

import org.apache.struts.*;
import org.apache.struts.action.ActionForm;

public class LoginBean extends ActionForm
{
    private String username;
    private String passwd;
    private String role;

    public LoginBean()
    {}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

