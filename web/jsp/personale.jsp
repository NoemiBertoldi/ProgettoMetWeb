<%@ page import="Beans.LoginBean" %>
<%@ page import="util.TableReader" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Personnel List</title>
    <jsp:include page="../util/login.jsp"/>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Personnel List</h1>
    </div>
    <div id="cont">
        <div id="left" class="left">
           <jsp:include page="../util/menu.jsp"/>
        </div>

        <div id="elenco" class="right">
            <table>
                <tr>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Fiscal Code</th>
                    <th>Date of Birth</th>
                </tr>
                <%
                    TableReader reader = new TableReader();
                    ResultSet table = reader.buildPersonnelTable(((LoginBean) session.getAttribute("LoginBean")).getUsername());

                    while(table.next())
                    {
                %>
                <tr>
                    <td><%= table.getString("nome") %></td>
                    <td><%= table.getString("cognome") %></td>
                    <td><%= table.getString("username") %></td>
                    <td><%= table.getString("ruolo").toUpperCase() %></td>
                    <td><%= table.getString("cf") %></td>
                    <td><%= table.getString("datanascita") %></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <div class="clear"/>
    </div>

    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>
