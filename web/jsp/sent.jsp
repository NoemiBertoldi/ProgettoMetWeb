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
            try
            {
                TableReader reader = new TableReader();
                LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));
                ResultSet table = reader.buildSentTable(role, bean.getUsername());
                String username, dest;
                int i = 0;
                while(table.next())
                {
                    i++;
            %>
            <table>
                <tr>
                    <th>Receiver</th>
                    <th>Subject</th>
                    <th>Date</th>
                    <th>Message</th>
                </tr>>
            <tr>
                <td>
                   <%
                        dest = table.getString("toOp");
                        if(dest == null)
                            dest = table.getString("toReg");
                    %>
                    <%= dest %>
                </td>

                <td>
                    <%= table.getString("oggetto") %>
                </td>
                <td>
                    <%= table.getString("dt_invio") %>
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
                <h3 style="text-align: center">NO MAIL SENT. Do you want to send a new mail? <a href="<%=request.getContextPath()%>/jsp/new.jsp">Here's the link!</a></h3>
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

</body>
</html>
