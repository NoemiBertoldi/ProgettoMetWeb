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
            query = "SELECT idpharm FROM personnel where username = '" + username + "'";
            table = reader.getTable(query);
            while(table.next())
                this.idFarmacia = table.getInt("idpharm");
        }
    }

    public int getAcqTotali() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT COUNT(*) AS countreg FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " WHERE purchases.completed = true";

        queryTf = "SELECT COUNT(*) AS counttf FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " WHERE personnel.idpharm = " + idFarmacia + " AND purchases.completed = true";



        return getTable(queryTf, queryReg);
    }

    public int getTotVend() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT SUM(cart.qty) AS countreg FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch"
                + " WHERE purchases.completed = true";

        queryTf = "SELECT SUM(cart.qty) AS counttf FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch"
                + " WHERE personnel.idpharm = " + idFarmacia + "AND purchases.completed = true";

        return getTable(queryTf, queryReg);
    }

    public int getTotRicetteAcq() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT SUM(cart.qty) AS countreg FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch JOIN prescriptions on cart.id = prescriptions.idcart"
                + " WHERE purchases.completed = true";

        queryTf = "SELECT SUM(cart.qty) AS counttf FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch JOIN prescriptions on cart.id = prescriptions.idcart"
                + " WHERE personnel.idpharm = " + idFarmacia + "AND purchases.completed = true";

        return getTable(queryTf, queryReg);
    }

    public int getTotRicette() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT COUNT(*) AS countreg FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch JOIN prescriptions on cart.id = prescriptions.idcart"
                + " WHERE purchases.completed = true";

        queryTf = "SELECT COUNT(*) AS counttf FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch JOIN prescriptions on cart.id = prescriptions.idcart"
                + " WHERE personnel.idpharm = " + idFarmacia + "AND purchases.completed = true";

        return getTable(queryTf, queryReg);
    }

    public float getAvg() throws SQLException
    {
        String queryTf, queryReg;

        queryReg = "SELECT AVG(cart.qty) AS countreg FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch JOIN prescriptions on cart.id = prescriptions.idcart"
                + " WHERE purchases.completed = true";

        queryTf = "SELECT AVG(cart.qty) AS counttf FROM purchases JOIN personnel on purchases.cfpers = personnel.cf"
                + " JOIN cart on purchases.codpurch = cart.codpurch JOIN prescriptions on cart.id = prescriptions.idcart"
                + " WHERE personnel.idpharm = " + idFarmacia + "AND purchases.completed = true";

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
