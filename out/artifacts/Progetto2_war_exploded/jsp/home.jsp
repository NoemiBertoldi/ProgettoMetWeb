<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Private Area</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <%
        if(! (LoginCheck.check((LoginBean) session.getAttribute("LoginBean"), request, null).equals("LOGIN_OK")))
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
            if (role.equals("reg"))
            { %>
        <h1>Region Home</h1>
        <% }
        else if(role.toUpperCase().equals("TF"))
        { %>
        <h1>Pharmacist Home</h1>
        <% }
        else if(role.toUpperCase().equals("DF"))
        { %>
        <h1>Doctor Home</h1>
        <% }
        else if(role.toUpperCase().equals("OB"))
        { %>
        <h1>Bench Operator Home</h1>
        <% }
        %>
    </div>

    <div id="cont">

        <div class = "clear">
            <div id = "left" class="left">
                <ul>
                    <li><a href="<%=request.getContextPath()%>/jsp/home.jsp">HOME</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/account.jsp">ACCOUNT</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/mail.jsp">MAIL</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/logout.jsp">LOGOUT</a></li>
                </ul>
            </div>
            <%
                if (role.equals("reg"))
                { %>
            <div id = "elenco" class="right">
                <ul>
                    <li><a href="<%=request.getContextPath()%>/jsp/regfarmacia.jsp">Register a New Pharmacy</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/analisi.jsp">Sales Analysis</a></li>
                </ul>
            </div>
                <% }
                else if(role.toUpperCase().equals("TF"))
                { %>
            <div id="elenco" class="right">
                <ul>
                    <li><a href="<%=request.getContextPath()%>/jsp/regpersonale.jsp">Register New Staff Member</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/personale.jsp">Personnel List</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/acquisto.jsp">Purchase Something</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/magazzino.jsp">Warehouse Management</a></li>
                    <li><a href="<%=request.getContextPath()%>/jsp/analisi.jsp">Sales Analysis</a></li>
                </ul>
            </div>
                <% }
                else if(role.toUpperCase().equals("DF") || role.toUpperCase().equals("OB"))
                { %>
            <div id="elenco" class="right"
                <ul>
                    <li><a href="<%=request.getContextPath()%>/jsp/acquisto.jsp">Purchase Something</a></li>
                </ul>
                <% }
                %>


        </div>
        </div>
    </div>
</div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>

</body>
</html>
