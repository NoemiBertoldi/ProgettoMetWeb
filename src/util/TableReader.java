package util;

import java.sql.*;

public class TableReader {
    private Connection connection;
    private Statement st;
    private ResultSet resultSet;

    public TableReader() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MetWeb_tab", "fedora", "fedora");
        } catch (Exception e) {
            System.out.println("No db connection");
            e.printStackTrace();
            connection.close();
        }
    }

    public ResultSet getTable(String query) throws SQLException {
        try {
            st = connection.createStatement();
            resultSet = st.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Query Error");
            e.printStackTrace();
            connection.close();

            return null;
        }

        return resultSet;
    }

    public boolean update(String query) throws SQLException {
        try {
            st = connection.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Query Error");
            e.printStackTrace();
            connection.close();
            return false;
        }
        return true;
    }

    public ResultSet buildPersonnelTable(String username) throws SQLException {
        ResultSet table;
        String query;
        int farmacia = -1;

        query = "SELECT idFarmacia FROM Operatori WHERE username = '" + username + "'";
        table = getTable(query);

        while (table.next())
            farmacia = table.getInt("idFarmacia");


        query = "SELECT * FROM Operatori WHERE idFarmacia = " + farmacia;
        table = getTable(query);

        return table;
    }

    public ResultSet buildWarehouseTable(String username) throws SQLException {
        ResultSet table;
        String query;
        int farmacia = -1;

        query = "SELECT idFarmacia FROM Operatori WHERE username = '" + username + "'";
        table = getTable(query);

        while (table.next())
            farmacia = table.getInt("idFarmacia");

        query = "select prodotti.nome, prodotti.descrizione,prodotti.prezzo,prodotti.conricetta,prodotti.codprodotto, magazzino.quantitaDisponibile "
                + "from operatori join farmacie on operatori.idFarmacia = farmacie.id "
                + "join magazzino on farmacie.id = magazzino.idFarmacia "
                + "join prodotti on magazzino.codProdotto = prodotti.codprodotto "
                + "where operatori.username = '" + username + "'";

        table = getTable(query);

        return table;
    }

    public ResultSet buildNewMail(String role, String username) throws SQLException
    {
        ResultSet table;
        String query;
        int farmacia = -1;

        if (role.toLowerCase().equals("reg")) {
            query = "SELECT nome AS username FROM farmacie";
        }
        else
        {
            query = "SELECT idFarmacia FROM operatori WHERE username = '" + username + "'";
            table= getTable(query);

            while (table.next())
                farmacia = table.getInt("idFarmacia");

            query = "SELECT username FROM operatori WHERE idFarmacia = " + farmacia;
        }
        return getTable(query);
    }
    public ResultSet buildInboxTable(String role, String username) throws SQLException
    {

        String query;

        if (role.toLowerCase().equals("reg"))
            query = "SELECT fromReg, fromOp, msg, oggetto, dt_invio FROM Messaggi WHERE toReg = '" + username + "'";
        else
            query = "SELECT fromReg, fromOp, msg, oggetto, dt_invio FROM Messaggi WHERE toOp = '" + username + "'";

        return getTable(query);
    }
    public ResultSet buildSentTable(String role, String username) throws SQLException
    {
        ResultSet table;
        String query;

        if (role.toLowerCase().equals("reg"))
            query = "SELECT toReg, toOp, msg, oggetto, dt_invio FROM Messaggi WHERE fromReg = '" + username + "'";
        else
            query = "SELECT toReg, toOp, msg, oggetto, dt_invio FROM Messaggi WHERE fromOp = '" + username + "'";

        return getTable(query);
    }
}