<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%=request.getContextPath()%>/css/home.css" rel="stylesheet" type="text/css">
    <title>Registered</title>
</head>
<body>
<%
    String msg = null;
    try
    {
        msg = (String) request.getAttribute("exitCode");
    }
    catch(Exception e)
    {
        msg = "Error";
    }
%>
<h1><%= msg%></h1>

<a href = "<%=request.getContextPath()%>/jsp/home.jsp">Back to Home</a>

</body>
</html>
