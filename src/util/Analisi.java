package util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Analisi
{
    private TableReader reader;
    private String role;
    private int idFarmacia;

    public Analisi(String username, String role) throws SQLException
    {
        String query;
        ResultSet table;

        this.reader = new TableReader();
        this.role = role;

        if (role.equals("tf"))
        {
            query = "SELECT idFarmacia FROM operatori where username = '" + username + "'";
            table = reader.getTable(query);
            while(table.next())
                this.idFarmacia = table.getInt("idFarmacia");
        }
    }

    public int getAcqTotali() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT COUNT(*) AS countreg FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " WHERE acquisti.completato = true";

        queryTf = "SELECT COUNT(*) AS counttf FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " WHERE operatori.idFarmacia = " + idFarmacia + " AND acquisti.completato = true";



        return getTable(queryTf, queryReg);
    }

    public int getTotVend() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT SUM(Carrello.quantita) AS countreg FROM Acquisti JOIN Operatori on Acquisti.cfOperatore = Operatori.cf"
                + " JOIN carrello on Acquisti.codAcquisto = Carrello.codAcquisto"
                + " WHERE Acquisti.completato = true";

        queryTf = "SELECT SUM(carrello.quantita) AS counttf FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " JOIN carrello on acquisti.codAcquisto = carrello.codAcquisto"
                + " WHERE operatori.idFarmacia = " + idFarmacia + "AND acquisti.completato = true";

        return getTable(queryTf, queryReg);
    }

    public int getTotRicetteAcq() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT SUM(carrello.quantita) AS countreg FROM Acquisti JOIN Operatori on Acquisti.cfOperatore = Operatori.cf"
                + " JOIN carrello on Acquisti.codAcquisto = Carrello.codAcquisto JOIN Ricette on Carrello.id = Ricette.idCarrello"
                + " WHERE Acquisti.completato = true";

        queryTf = "SELECT SUM(carrello.quantita) AS counttf FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " JOIN carrello on acquisti.codAcquisto = carrello.codAcquisto JOIN ricette on carrello.id = ricette.idCarrello"
                + " WHERE operatori.idFarmacia = " + idFarmacia + "AND acquisti.completato = true";

        return getTable(queryTf, queryReg);
    }

    public int getTotRicette() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT COUNT(*) AS countreg FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " JOIN carrello on acquisti.codAcquisto = carrello.codAcquisto JOIN ricette on carrello.id = ricette.idCarrello"
                + " WHERE acquisti.completato = true";

        queryTf = "SELECT COUNT(*) AS counttf FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " JOIN carrello on acquisti.codAcquisto = carrello.codAcquisto JOIN ricette on carrello.id = ricette.idCarrello"
                + " WHERE operatori.idFarmacia = " + idFarmacia + "AND acquisti.completato = true";

        return getTable(queryTf, queryReg);
    }

    public float getAvg() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT AVG(carrello.quantita) AS countreg FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " JOIN carrello on acquisti.codAcquisto = carrello.codAcquisto JOIN ricette on carrello.id = ricette.idCarrello"
                + " WHERE acquisti.completato = true";

        queryTf = "SELECT AVG(carrello.quantita) AS counttf FROM acquisti JOIN operatori on acquisti.cfOperatore = operatori.cf"
                + " JOIN carrello on acquisti.codAcquisto = carrello.codAcquisto JOIN ricette on carrello.id = ricette.idCarrello"
                + " WHERE operatori.idFarmacia = " + idFarmacia + "AND acquisti.completato = true";

        return getTable(queryTf, queryReg);
    }

    private int getTable(String queryTf, String queryReg) throws SQLException
    {
        String query = "";
        ResultSet table;
        int tot = 0;

        if(role.equals("tf")) {
            query = queryTf;
            table = reader.getTable(query);
            while(table.next())
                tot = table.getInt("counttf");
        }
        else if(role.equals("reg"))
        {
            query = queryReg;
            table = reader.getTable(query);
            while(table.next())
                tot = table.getInt("countreg");
        }

        return tot;
    }
}
