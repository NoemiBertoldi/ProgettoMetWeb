package util;

import java.sql.*;

public class TableReader
{
    private Connection connection;
    private Statement st;
    private ResultSet resultSet;

    public TableReader() throws SQLException
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MetWeb_tab", "fedora", "fedora");
        }
        catch(Exception e)
        {
            System.out.println("Errore connessione al DB");
            e.printStackTrace();
            connection.close();
        }
    }

    public ResultSet getTable(String query) throws SQLException
    {
        try
        {
            st = connection.createStatement();
            resultSet = st.executeQuery(query);
        }
        catch(Exception e)
        {
            System.out.println("Errore nella query");
            e.printStackTrace();
            connection.close();

            return null;
        }

        return resultSet;
    }

    public boolean update(String query) throws SQLException
    {
        try
        {
            st = connection.createStatement();
            st.executeUpdate(query);
        }
        catch(Exception e)
        {
            System.out.println("Query Error");
            e.printStackTrace();
            connection.close();
            return false;
        }
        return true;
    }

    public ResultSet buildPersonnelTable(String username) throws SQLException
    {
        ResultSet table;
        String query;
        int farmacia = -1;

        query = "SELECT idFarmacia FROM Operatori WHERE username = '" + username + "'";
        table = getTable(query);

        while(table.next())
            farmacia = table.getInt("idFarmacia");


        query = "SELECT * FROM Operatori WHERE idFarmacia = " + farmacia;
        table = getTable(query);

        return table;
    }

    public ResultSet buildWarehouseTable(String username) throws SQLException
    {
        ResultSet table;
        String query;
        int farmacia = -1;

        query = "SELECT idFarmacia FROM Operatori WHERE username = '" + username + "'";
        table = getTable(query);

        while(table.next())
            farmacia = table.getInt("idFarmacia");

        query = "select prodotti.nome, prodotti.descrizione, prodotti.immagine, magazzino.quantitaDisponibile "
                + "from operatori join farmacie on operatori.idFarmacia = farmacie.id "
                + "join magazzino on farmacie.id = magazzino.idFarmacia "
                + "join prodotti on magazzino.codProdotto = prodotti.codprodotto "
                + "where operatori.username = '" + username + "'";

        table = getTable(query);

        return table;
    }
}
