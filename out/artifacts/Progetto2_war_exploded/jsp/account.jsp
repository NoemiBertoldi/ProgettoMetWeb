<%@ page import="util.TableReader" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="Beans.LoginBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
    <jsp:include page="../util/login.jsp"/>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Account</h1>
    </div>
    <div id="cont">

        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>

        <div id="elenco"class="right">
            <%
            TableReader reader = new TableReader();
            LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));
            ResultSet table = reader.buildAccountTable(bean.getUsername());

            while(table.next())
            {
            %>
            <div class="clear">
                <div class="tleft">
                    Name:
                </div>
                <div class="tright">
                    <%=table.getString("name")%>
                </div>
            </div>
            <div class="clear">
                <div class="tleft">
                    Surname:
                </div>
                <div class="tright">
                    <%=table.getString("surname")%>
                </div>
            </div>
            <div class="clear">
                <div class="tleft">
                    Username
                </div>
                <div class="tright">
                    <%=table.getString("username")%>
                </div>
            </div>
            <div class="clear">
                <div class="tleft">
                    Works at:
                </div>
                <div class="tright">
                    <%=table.getString("pharmname")%>
                </div>
            </div>
            <%
            }
            %>
        </div>
        <div class="clear"/>

    </div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>

</div>
</body>
</html>

