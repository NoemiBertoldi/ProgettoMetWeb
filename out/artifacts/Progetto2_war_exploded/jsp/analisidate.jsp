<%@ page import="Beans.LoginBean" %>
<%@ page import="util.Analisi" %>
<%@ page import="util.LoginCheck" %>
<%@ page import="util.AnalisiDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
            <table>
                <tr>
                    <th>All purchases</th>
                    <th>All product sold</th>
                    <th>Prescribed</th>
                    <th>Prescriptions</th>
                    <th>Average number of drugs per prescription</th>
                </tr>

                <%
                    try
                    {
                        String username = ((LoginBean)session.getAttribute("LoginBean")).getUsername();
                        String datai= (String) request.getAttribute("datai");
                        String dataf= (String) request.getAttribute("dataf");
                        AnalisiDate a = null;

                        if(role.equalsIgnoreCase("tf"))
                            a = new AnalisiDate(username, "tf", datai,dataf);
                        else if(role.equalsIgnoreCase("reg"))
                            a = new AnalisiDate(username, "reg", datai,dataf);
                %>
                <tr>
                    <td><%= a.getAcqTotali() %></td>
                    <td><%= a.getTotVend() %></td>
                    <td><%= a.getTotRicetteAcq() %></td>
                    <td><%= a.getTotRicette() %></td>
                    <td><%= a.getAvg() %></td>
                </tr>
                <%
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                %>
            </table>
        </div>
        <div class="clear"/>

    </div>
    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>
