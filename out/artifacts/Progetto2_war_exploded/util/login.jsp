<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>
<html>
<body>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<%
    if(! (LoginCheck.check((LoginBean) session.getAttribute("LoginBean"), request, null).equals("LOGIN_OK")))
    {
        request.setAttribute("exitCode", "Couldn't log in");
%>

<script type="text/javascript">
    window.location.replace('<%=request.getContextPath()%>/jsp/error.jsp');
</script>
<%
    }
%>
</body>
</html>
