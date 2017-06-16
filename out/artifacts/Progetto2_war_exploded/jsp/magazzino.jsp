<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>
<%@ page import="util.TableReader" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="<%=request.getContextPath()%>/css/home.css" rel="stylesheet" type="text/css">

    <title>WareHouse</title>

    <%
        if(! (LoginCheck.check((LoginBean) session.getAttribute("LoginBean"), request, "pers").equals("LOGIN_OK")))
        {
            request.setAttribute("exitCode", "Couldn't log in");
    %>

    <script type="text/javascript">
        window.location.replace('error.jsp');
    </script>
    <%
        }
    %>

    <% String role = (String) request.getSession().getAttribute("role"); %>

</head>
<body>
<div id="container">
    <div id="header">

        <%
            if(((String) request.getSession().getAttribute("role")).toLowerCase().equals("tf"))
            { %>
        <h1>Warehouse Management</h1>
        <%}
        else
        { %>
        <h1>Warehouse Product List</h1>
        <%}
        %>

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
            <th>Product Name</th>
            <th>Description</th>
            <th>Available Quantity </th>
            <th>Prescription</th>
            <%
                if(role.toLowerCase().equals("tf"))
                { %>
            <th>Quantity</th>
            <th>Order</th>
            <% }
            %>
        </tr>
        <%

            TableReader reader = new TableReader();
            LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));

            ResultSet table = reader.buildWarehouseTable(bean.getUsername());

            while(table.next())
            {
        %><tr>
        <td><%= table.getString("nome") %></td>
        <td><%= table.getString("descrizione") %></td>
        <td><%= table.getString("quantitaDisponibile") %></td>
        <td><%= table.getBoolean("conRicetta") %></td>
        <%
            if(role.toLowerCase().equals("tf"))
            { %>
        <form action="<%=request.getContextPath()%>/riempiMag.do" method="post" name="form">
            <td>
                <input type="number" name="quantita" id="quantita" min="1" max="50">
            </td>
            <td>
                <input type="submit" value="Order">
                <input type="text" name="productName" id="productName" value="<%= table.getString("nome") %>"
                       style="visibility:hidden">
            </td>
        </form>
        <% }
        %>
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
