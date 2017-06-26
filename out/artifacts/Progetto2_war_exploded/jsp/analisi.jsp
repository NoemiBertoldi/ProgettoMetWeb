<%@ page import="Beans.LoginBean" %>
<%@ page import="util.Analisi" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Analysis</title>
    <%
        String role="";
    %>
    <jsp:include page="../util/login.jsp"/>
    <%
        role=(String) request.getSession().getAttribute("role");
        if((! role.equals("reg")) && (! role.equals("tf")))
        {
            request.setAttribute("exitCode", "This area is reserved to: REG and TF");
    %>
        <script type="text/javascript">
            window.location.replace('error.jsp');
        </script>
    <%
        }
    %>

</head>
<body>

<div id="container">

    <div id="header">
        <% if(role.equals("pers")) {%>
        <h1>Pharmacy Sales Analysis</h1>
        <%} else if(role.equals("reg")) {%>
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
                        LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));
                        Analisi a = null;

                        if(role.equals("tf"))
                            a = new Analisi(bean.getUsername(), "tf");
                        else if(role.equals("reg"))
                            a = new Analisi(bean.getUsername(), "reg");
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
