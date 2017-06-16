<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
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

        <div id="elenco"class="right">

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

