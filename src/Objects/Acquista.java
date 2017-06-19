package Objects;

public class Acquista
{
    private String cfOp;
    private String formatDate;
    private int idFarmacia;

    public Acquista(String cfOp, String date, int idFarmacia)
    {
        this.cfOp = cfOp;
        this.formatDate = date;
        this.idFarmacia = idFarmacia;
    }

    public String getCfOp() { return cfOp; }

    public String getFormatDate() {
        return formatDate;
    }

    public int getIdFarmacia() { return idFarmacia; }
}