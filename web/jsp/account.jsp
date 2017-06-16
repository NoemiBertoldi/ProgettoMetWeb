<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
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

</head>
<body>
<div id="container">
    <div id="header">
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
        <div id="elenco"class="right">

        </div>
        </div>

    </div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>

