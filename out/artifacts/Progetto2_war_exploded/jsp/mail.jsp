<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mail</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <jsp:include page="../util/login.jsp"/>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Mail Service</h1>
    </div>
    <div id="cont">
        <div id="left" class="left">
           <jsp:include page="../util/menu.jsp"/>
        </div>
        <div id="elenco" class="right">
            <a href="<%= request.getContextPath()%>/jsp/inbox.jsp">Inbox mail</a><br>
            <a href="<%= request.getContextPath()%>/jsp/sent.jsp">Sent mail</a><br>
            <a href="<%= request.getContextPath()%>/jsp/new.jsp">Write new mail</a>
        </div>
        <div class="clear"/>
    </div>

    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>

<%
    String msg = (String) request.getSession().getAttribute("msg");

    if(msg != null)
    {
%>
<script>
    alert("<%= msg %>");
</script>
<%
        request.getSession().removeAttribute("msg");
    }
%>

</body>
</html>

