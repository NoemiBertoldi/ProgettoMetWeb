<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String role = (String) request.getSession().getAttribute("role");
    %>
</head>
<body>
<ul>
    <li><a href="<%=request.getContextPath()%>/jsp/home.jsp">HOME</a></li>
    <%
        if(!role.equalsIgnoreCase("reg"))
        {
    %>
    <li><a href="<%=request.getContextPath()%>/jsp/account.jsp">ACCOUNT</a></li>
    <%
        }
    %>
    <li>MAIL SERVICE
        <ul>
            <li><a href="<%=request.getContextPath()%>/jsp/new.jsp">NEW MAIL</a></li>
            <li><a href="<%= request.getContextPath()%>/jsp/inbox.jsp">INBOX MAIL</a></li>
            <li><a href="<%= request.getContextPath()%>/jsp/sent.jsp">SENT MAIL</a></li>
        </ul>
    </li>
    <li><a href="<%=request.getContextPath()%>/jsp/logout.jsp">LOGOUT</a></li>
</ul>
</body>
</html>
