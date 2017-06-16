<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Analysis</title>
    <%
        String role="";
    %>
    <jsp:include page="../util/login.jsp"/>
    <%
        role=(String) request.getSession().getAttribute("role");
    %>

</head>
<body>

<div id="container">

    <div id="header">
        <% if(role.equals("pers")) {%>
        <h1>Pharmacy Sales Analysis</h1>
        <%} else {%>
        <h1>Pharmacies Sales Analysis</h1>
        <% } %>
    </div>

    <div id="cont">

        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>

        <div id="elenco" class="right">
        <%
            if(role.equals("pers"))
            {
        %>

        <!-- tabella -->


        <%
            }
            else
            {
        %>

        <!-- -->


        <%
            }
        %>

        </div>

        <div class="clear"/>

    </div>
    <div id= "footer">
        <script>
            $("#footer").load("../util/footer.html");
        </script>
    </div>
</div>
</body>
</html>
