package Objects;
import java.util.Date;
public class Acquista
{

    private String cfOp;
    private Date date;
    private int idFarmacia;

    public Acquista(String cfOp, Date date, int idFarmacia)
    {
        this.cfOp = cfOp;
        this.date = date;
        this.idFarmacia = idFarmacia;
    }

    public String getCfOp() { return cfOp; }

    public Date getDate() {
        return date;
    }

    public int getIdFarmacia() { return idFarmacia; }
}