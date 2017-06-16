<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%=request.getContextPath()%>/css/home.css" rel="stylesheet" type="text/css">
    <title>Error Page</title>
</head>
<body>
<%
    String errorMsg = null;
    try
    {
        errorMsg = (String) request.getAttribute("exitCode");
        if(errorMsg == null)
            errorMsg = "Couldn't log in";
    }
    catch(Exception e)
    {
        errorMsg = "Couldn't log in";
    }
%>
<h1><%= errorMsg%></h1>
<%
    if(errorMsg.equals("Couldn't log in"))
    {
%>
<a href = "<%=request.getContextPath()%>/index.html">Back to the Login Page</a>
<%
    }
    else
    {
%>
<a href = "<%=request.getContextPath()%>/jsp/home.jsp">Back to Home</a>
<%
    }
%>
</body>
</html>
