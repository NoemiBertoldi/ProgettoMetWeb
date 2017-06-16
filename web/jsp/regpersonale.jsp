<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Personnel Enrollment</title>
    <jsp:include page="../util/login.jsp"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/javascript/validazione.js"></script>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Enroll New Personnel</h1>
    </div>

    <div id="cont">

        <div id="left" class="left">
           <jsp:include page="../util/menu.jsp"/>
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

        <div class="clear"/>
    </div>

    <div id= "footer">
        <script>
            $("footer").load("../util/footer.html");
        </script>
    </div>
</div>
</body>
</html>
