<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Private Area</title>
    <jsp:include page="../util/login.jsp"/>
    <%
        String role = (String) request.getSession().getAttribute("role");
    %>
</head>
<body>
<div id="container">
    <div id="header">
        <%
            if (role.equals("reg"))
            {
        %>
        <h1>Region Home</h1>
        <%
            }
            else if(role.toUpperCase().equals("TF"))
            {
        %>
        <h1>Pharmacist Home</h1>
        <%
            }
            else if(role.toUpperCase().equals("DF"))
            {
        %>
        <h1>Doctor Home</h1>
        <%
            }
            else if(role.toUpperCase().equals("OB"))
            {
        %>
        <h1>Bench Operator Home</h1>
        <%
            }
        %>
    </div>

    <div id="cont">

        <div id = "left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>
        <%
            if (role.equals("reg"))
            {
        %>
        <div id = "elenco" class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/jsp/regfarmacia.jsp">Register a New Pharmacy</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/analisi.jsp">Sales Analysis</a></li>
            </ul>
        </div>
        <%
            }
            else if(role.toUpperCase().equals("TF"))
            {
        %>
        <div id="elenco" class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/jsp/regpersonale.jsp">Register New Staff Member</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/personale.jsp">Personnel List</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/acquisto.jsp">Purchase Something</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/magazzino.jsp">Warehouse Management</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/analisi.jsp">Sales Analysis</a></li>
            </ul>
        </div>
        <%
            }
            else if(role.toUpperCase().equals("DF") || role.toUpperCase().equals("OB"))
            {
        %>
        <div id="elenco" class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/jsp/acquisto.jsp">Purchase Something</a></li>
            </ul>
        </div>
        <%
            }
        %>

        <div class = "clear"/>
    </div>

    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>
