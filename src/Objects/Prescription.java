package Objects;

public class Prescription
{
    private String codProdotto;
    private int qty;

    public Prescription(String codProdotto, int qty)
    {
        this.codProdotto = codProdotto;
        this.qty = qty;
    }

    public String getCodProdotto()
    {
        return codProdotto;
    }

    public void setCodProdotto(String codProdotto)
    {
        this.codProdotto = codProdotto;
    }

    public int getQty()
    {
        return qty;
    }

    public void setQty(int qty)
    {
        this.qty = qty;
    }
}
