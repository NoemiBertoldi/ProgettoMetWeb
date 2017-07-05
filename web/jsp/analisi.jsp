<%@ page import="util.LoginCheck" %>
<%@ page import="Beans.LoginBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sales Analysis</title>
</head>

<title>Analysis</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <%
        String role="";
    %>
    <%
        if( (LoginCheck.check((LoginBean) session.getAttribute("LoginBean"), request, null).equals("LOGIN_OK")))
        {
            role=(String) request.getSession().getAttribute("role");
            if((! role.equalsIgnoreCase("reg")) && (! role.equalsIgnoreCase("tf")))
            {
                request.setAttribute("exitCode", "This area is reserved to: REG and TF");
    %>
<script type="text/javascript">
    window.location.replace('<%=request.getContextPath()%>/error.jsp');
</script>
    <%
        }
    }
        else
        {
    %>
<script type="text/javascript">
    window.location.replace('<%=request.getContextPath()%>/jsp/error.jsp');
</script>
    <%
        }
    %>


</head>
<body>

<div id="container">

    <div id="header">
        <% if(role.equalsIgnoreCase("tf")) {%>
        <h1>Pharmacy Sales Analysis</h1>
        <%} else if(role.equalsIgnoreCase("reg")) {%>
        <h1>Pharmacies Sales Analysis</h1>
        <% } %>
    </div>
    <div id="cont">

        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>

        <div id="elenco" class="right">
            If you want to check how the sales are going in general click <a href="<%=request.getContextPath()%>/jsp/analisitot.jsp">here</a><br><br>
            If you want to check how the sales were/are going in a certain perion, just fill this form and click on "SEND"
            <form action="<%=request.getContextPath()%>/analisi.do" method="post" name="form" onsubmit="return validateAnalysis()">
                <div class="clear">
                <div class="tleft">
                    Start Date (dd-mm-yyyy)
                </div>
                <div class="tright">
                    <input type="text" name="datai">
                </div>
                </div>
                <div class="clear">
                    <div class="tleft">
                        End Date (dd-mm-yyyy)
                    </div>
                    <div class="tright">
                        <input type="text" name="dataf">
                    </div>
                </div>
                <input type="submit" value="SEND">
            </form>
        </div>
        <div class="clear"/>
    </div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>

</body>
</html>
