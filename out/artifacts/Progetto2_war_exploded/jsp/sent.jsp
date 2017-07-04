<%@ page import="util.TableReader" %>
<%@ page import="Beans.LoginBean" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="util.LoginCheck" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inbox Mail</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <%
        if(! (LoginCheck.check((LoginBean) session.getAttribute("LoginBean"), request, null).equals("LOGIN_OK")))
        {
    %>

    <script type="text/javascript">
        window.location.replace('<%=request.getContextPath()%>/jsp/error.jsp');
    </script>
    <%
        }
    %>
    <%
        String role = ((String) request.getSession().getAttribute("role")).toLowerCase();
    %>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Inbox Mail</h1>
    </div>
    <div id="cont">
        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>
        <div id="elenco" class="right">

            <%
                int i=0;
            try
            {
                TableReader reader = new TableReader();
                LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));
                ResultSet table = reader.buildSentTable(role, bean.getUsername());

                String username, dest;

                while(table.getString("msg")!=null)
                {
                    i++;
            %>

            <div class="clear">
                <div class="tleft">
                    <b>Receiver: </b>
                    <%
                        dest = table.getString("fromOp");
                        if(dest == null)
                            dest = table.getString("fromReg");
                    %>
                </div>
                <div class="tright">
                    <%= dest %>
                </div>
            </div>

            <div class="clear">
                <div class="tleft">
                    <b>Subject: </b>
                </div>
                <div class="tright">
                    <%= table.getString("oggetto") %>
                </div>
            </div>

            <div class="clear">
                <div class="tleft">
                    <b>Date: </b>
                </div>
                <div class="tright">
                    <%= table.getString("dt_invio") %>
                </div>
            </div>

            <div class="clear">
                <div class="tleft">
                    <b>Message: </b>
                </div>
                <div class="tright">
                    <%= table.getString("msg") %>
                </div>
            </div>
                        <%
                    }
                }
                catch(Exception e)
                {

                }%>
            <%
                if(i == 0)
                {
            %>
            <h3 style="text-align: center">NO MAIL SENT. Do you want to send a new mail? <a href="<%=request.getContextPath()%>/jsp/new.jsp">Here's the link!</a></h3>
            <%
                }
            %>
        </div>
        <div class="clear"/>
    </div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>

</body>
</html>
