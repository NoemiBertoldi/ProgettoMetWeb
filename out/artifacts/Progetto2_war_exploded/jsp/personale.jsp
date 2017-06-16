<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>
<%@ page import="util.TableReader" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="<%=request.getContextPath()%>/css/home.css" rel="stylesheet" type="text/css">

    <title>Personnel List</title>

    <%
        if(! (LoginCheck.check((LoginBean) session.getAttribute("LoginBean"), request, "tf").equals("LOGIN_OK")))
        {
            request.setAttribute("exitCode", "Couldn't log in");
    %>

    <script type="text/javascript">
        window.location.replace('error.jsp');
    </script>
    <%
        }
    %>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Personnel List</h1>
    </div>
    <div id="cont">
        <div class="clear">
        <div id="left" class="left">
            <ul>
                <li><a href="<%=request.getContextPath()%>/jsp/home.jsp">HOME</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/account.jsp">ACCOUNT</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/mail.jsp">MAIL</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/logout.jsp">LOGOUT</a></li>
            </ul>
        </div>

        <div id="elenco" class="right">
        <table style="width:100%">
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Username</th>
                <th>Role</th>
                <th>Fiscal Code</th>
                <th>Date of Birth</th>
                <th>Regional Code</th>
            </tr>
            <%
                TableReader reader = new TableReader();
                ResultSet table = reader.buildWarehouseTable(((LoginBean) session.getAttribute("LoginBean")).getUsername());

                while(table.next())
                {
            %><tr>
            <td><%= table.getString("nome") %></td>
            <td><%= table.getString("cognome") %></td>
            <td><%= table.getString("username") %></td>
            <td><%= table.getString("ruolo").toUpperCase() %></td>
            <td><%= table.getString("cf") %></td>
            <td><%= table.getString("datanascita") %></td>
            <td><%= table.getString("codregionale") %></td>
        </tr>
            <%}
            %>
        </table>
        </div>
        </div>
    </div>



    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>
