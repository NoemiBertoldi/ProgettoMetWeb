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

        query = "SELECT idpharm FROM personnel WHERE username = '" + username + "'";
        table = getTable(query);

        while (table.next())
            farmacia = table.getInt("idpharm");


        query = "SELECT * FROM personnel WHERE idpharm = " + farmacia;
        table = getTable(query);

        return table;
    }

    public ResultSet buildWarehouseTable(String username) throws SQLException {
        ResultSet table;
        String query;
        int farmacia = -1;

        query = "SELECT idpharm FROM personnel WHERE username = '" + username + "'";
        table = getTable(query);

        while (table.next())
            farmacia = table.getInt("idpharm");

        query = "select products.nome, products.descr,products.price,products.needpres,products.codprod, warehouse.availqty "
                + "from personnel join pharmacies on personnel.idpharm = pharmacies.id "
                + "join warehouse on pharmacies.id = warehouse.idpharm "
                + "join products on warehouse.codprod = products.codprod "
                + "where personnel.username = '" + username + "'";

        table = getTable(query);

        return table;
    }

    public ResultSet buildNewMail(String role, String username) throws SQLException
    {
        ResultSet table;
        String query;
        int farmacia = -1;

        if (role.toLowerCase().equals("reg")) {
            query = "SELECT nome AS username FROM pharmacies";
        }
        else
        {
            query = "SELECT idpharm FROM personnel WHERE username = '" + username + "'";
            table= getTable(query);

            while (table.next())
                farmacia = table.getInt("idpharm");

            query = "SELECT username FROM personnel WHERE idpharm = " + farmacia;
        }
        return getTable(query);
    }
    public ResultSet buildInboxTable(String role, String username) throws SQLException
    {

        String query;

        if (role.toLowerCase().equals("reg"))
            query = "SELECT fromReg, fromOp, msg, obj, datesent FROM messages WHERE toReg = '" + username + "'";
        else
            query = "SELECT fromReg, fromOp, msg, obj, datesent FROM messages WHERE toOp = '" + username + "'";

        return getTable(query);
    }
    public ResultSet buildSentTable(String role, String username) throws SQLException
    {
        ResultSet table;
        String query;

        if (role.toLowerCase().equals("reg"))
            query = "SELECT toReg, toOp, msg, obj, datesent FROM messages WHERE fromReg = '" + username + "'";
        else
            query = "SELECT toReg, toOp, msg, obj, datesent FROM messages WHERE fromOp = '" + username + "'";

        return getTable(query);
    }
}