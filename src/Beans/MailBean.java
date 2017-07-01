package Beans;

import org.apache.struts.action.ActionForm;

public class MailBean extends ActionForm
{
    private String[] username;
    private String obj;
    private String text;


    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public MailBean()
    { }

    public String[] getUsername() {
        return username;
    }

    public void setUsername(String[] username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
