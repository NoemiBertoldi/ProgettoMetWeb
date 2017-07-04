<%@ page import="util.TableReader" %>
<%@ page import="Beans.LoginBean" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inbox Mail</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <jsp:include page="../util/login.jsp"/>
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
                ResultSet table = reader.buildInboxTable(role, bean.getUsername());
                String username, mitt;

                while(table.next())
                {
                    i++;
            %>
            <div class="clear">
                <div class="tleft">
                    <b>Sender: </b>
                    <%
                        mitt = table.getString("fromOp");
                        if(mitt == null)
                            mitt = table.getString("fromReg");
                    %>
                </div>
                <div class="tright">
                    <%= mitt %>
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

            }
                    if(i == 0)
                    {
                %>
            <h3 style="text-align: center">You haven't received any email</h3>
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
