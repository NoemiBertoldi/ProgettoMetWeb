<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Personnel Enrollment</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <%
        if(! (LoginCheck.check((LoginBean) session.getAttribute("LoginBean"), request, "tf").equals("LOGIN_OK")))
        {
            request.setAttribute("exitCode", "Couldn't log in");
    %>

    <script type="text/javascript">
        window.location.replace('error.jsp');
    </script>
    <%
        }
    %>

    <script type="text/javascript" src="<%=request.getContextPath()%>/javascript/validazione.js"></script>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Enroll New Personnel</h1>
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

        <div id="elenco" class="right">
        <form action="<%=request.getContextPath()%>/regpers.do" method="post" name="form" onsubmit="return validatePersonnelForm()">
            <div id="center" class="fcenter">
            <div class="clear">
            <div class="tleft">Name</div>
            <div class="tright"><input type="text" name="nome" id="nome" required></div>
            </div>
            <div class="clear">
            <div class="tleft">Surname</div>
            <div class="tright"><input type="text" name="cognome" id="cognome" required></div>
            </div>
            <div class="clear">
            <div class="tleft">Fiscal Code</div>
            <div class="tright"><input type="text" name="cf" id="cf" required></div>
            </div>
            <div class="clear">
            <div class="tleft">Username</div>
            <div class="tright"><input type="text" name="username" id="username" required></div>
            </div>
            <div class="clear">
            <div class="tleft">Password</div>
            <div class="tright"><input type="password" name="password" id="password" required></div>
            </div>
            <div class="clear">
            <div class="tleft">Confirm Password</div>
            <div class="tright"><input type="password" name="passwordConfirm" id="passwordConfirm" required></div>
            </div>
            <div class="clear">
            <div class="tleft">Date of Birth (dd-mm-yyyy)</div>
            <div class="tright"><input type="text" name="dataNascita" id="dataNascita"></div>
            </div>
            <div class="clear">
            <div class="tleft"><input type="radio" name="role" value="df" checked="checked"></div>
            <div class="tright">Doctor Pharmacist</div>
            </div>
            <div class="clear">
            <div class="tleft"><input type="radio" name="role" value="ob"></div>
            <div class="tright">Bench Operator</div>
            </div>
            <div class="center"><input type="submit" value="Enroll"></div>
            </div>
        </form>
        </div>

        </div>
    </div>

    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>
