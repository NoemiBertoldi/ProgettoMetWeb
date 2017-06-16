<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enroll New Pharmacy</title>
    <script type="text/javascript" src="../javascript/validazione.js"></script>
    <jsp:include page="../util/login.jsp"/>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Enroll New Pharmacy</h1>
    </div>

    <div id="cont">
        <div class="clear">
        <div id="left" class="left">
           <jsp:include page="../util/menu.jsp"/>
        </div>

        <div id="elenco" class="right">
            <form action="<%=request.getContextPath()%>/regfarm.do" method="post" name="form" onsubmit="return validatePharmacyForm()">
                <div class="fleft">
                    <h4>(Chief) Pharmacist Data: </h4>
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
                <div class="tleft">Conferma Password</div>
                <div class="tright"><input type="password" name="passwordConfirm" id="passwordConfirm" required></div>
                </div>
                <div class="clear">
                <div class="tleft">Date of birth (dd-mm-yyyy)</div>
                <div class="tright"><input type="text" name="dataNascita" id="dataNascita"></div>
                </div>
            </div>
            <div class="fright">
                <h4>Pharmacy Data</h4>
                <div class="clear">
                <div class="tleft">Name</div>
                <div class="tright"><input type="text" name="nomeF" id="nomeF" required></div>
                </div>
                <div class="clear">
                <div class="tleft">Address</div>
                <div class="tright"><input type="text" name="indirizzo" id="indirizzo" required></div>
                </div>
                <div class="clear">
                <div class="tleft">Telephone Number</div>
                <div class="tright"><input type="text" name="telefono" id="telefono" required></div>
                </div>
                <br><br><br><br>
            </div>
            <div class="center"><input type="submit" value="Submit"></div>
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
