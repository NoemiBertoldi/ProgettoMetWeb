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
    </div>
    <div id="cont">
        <div id="left" class="left">
           <jsp:include page="../util/menu.jsp"/>
        </div>
        <div id="elenco" class="right">

        </div>
        <div class="clear"/>
    </div>

    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>

