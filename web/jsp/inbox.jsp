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
            <table>
                <tr>
                    <th>Sender</th>
                    <th>Subject</th>
                    <th>Date</th>
                    <th>Message</th>
                </tr>
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

                <tr>
                    <td>
                        <%
                            mitt = table.getString("fromOp");
                            if(mitt == null)
                                mitt = table.getString("fromReg");
                        %>
                        <%= mitt %>
                    </td>

                    <td>
                        <%= table.getString("obj") %>
                    </td>
                    <td>
                        <%= table.getString("datesent") %>
                    </td>
                    <td>
                        <%= table.getString("msg") %>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <%
                if(i == 0)
                {
            %>
            <h3 style="text-align: center">You haven't received any email</h3>
            <%
                    }

                }
                catch(Exception e)
                {

                }%>
        </div>
        <div class="clear"/>
    </div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>
