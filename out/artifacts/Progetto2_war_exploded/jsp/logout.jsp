<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>

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
    Logging out

    <%
        request.getSession().removeAttribute("RegisterBean");
    %>
    <script type="text/javascript">
        window.location.replace('<%=request.getContextPath()%>/index.html');
    </script>
</div>
</body>
</html>

