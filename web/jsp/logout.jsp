<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
    <jsp:include page="../util/login.jsp"/>
</head>
<body>
<div id="container">
    <%
        request.getSession().removeAttribute("RegisterBean");
    %>
    <script type="text/javascript">
        window.location.replace('<%=request.getContextPath()%>/index.html');
    </script>
</div>
</body>
</html>

