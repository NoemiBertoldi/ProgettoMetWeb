package Beans;
import org.apache.struts.action.ActionForm;

public class MagBean extends ActionForm
{
    private String qty, nomeprodotto;

    public MagBean()
    {}

    public String getQty()
    {
        return qty;
    }

    public void setQty(String qty)
    {
        this.qty = qty;
    }

    public String getNomeprodotto()
    {
        return nomeprodotto;
    }

    public void setNomeprodotto(String nomeprodotto)
    {
        this.nomeprodotto = nomeprodotto;
    }
}
