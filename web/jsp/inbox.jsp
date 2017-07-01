<%@ page import="util.TableReader" %>
<%@ page import="Beans.LoginBean" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inbox Mail</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <jsp:include page="../util/login.jsp"/>
    <%
        String role = ((String) request.getSession().getAttribute("role")).toLowerCase();
    %>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Inbox Mail</h1>
    </div>
    <div id="cont">
        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>
        <div id="elenco" class="right">
            <!-- tABELLA PER LE MAIL -->
            <br>
                <%
            try
            {
                TableReader reader = new TableReader();
                LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));
                ResultSet table = reader.buildInboxTable(role, bean.getUsername());
                String username, mitt;





                //box vuota






                while(table.next())
                {
        %>
            <h4>Sender: </h4>
                <%
            mitt = table.getString("fromOp");
            if(mitt == null)
                mitt = table.getString("fromReg");
        %>
                <%= mitt %>

            <br>
            <h4>Subject: </h4>
                <%= table.getString("oggetto") %>

            <br>
            <h4>Date: </h4>
                <%= table.getString("dt_invio") %>

            <br>
            <h4>Message: </h4>
            <br>
                <%= table.getString("msg") %>
                <%
                }
            }
            catch(Exception e)
            {

            }
        %>
        </div>
        <div class="clear"/>
    </div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>

</body>
</html>
