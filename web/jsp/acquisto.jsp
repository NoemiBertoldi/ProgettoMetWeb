<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.TableReader" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="Beans.LoginBean" %>
<html>
<head>
    <title>Purchase</title>
    <jsp:include page="../util/login.jsp"/>

</head>
<body>

<div id="container">
    <div id="header">
        <h1>Purchase</h1>
    </div>
    <div id="cont">

        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"></jsp:include>
        </div>

        <div id="elenco" class="right">
            <table>
                <tr>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Available Quantity</th>
                    <th>Prescription</th>
                    <th>Quantity</th>
                </tr>

                <%
                    TableReader reader = new TableReader();
                    ResultSet table = reader.buildWarehouseTable(((LoginBean) session.getAttribute("LoginBean")).getUsername());

                    while(table.next())
                    {
                %>
                <tr>
                    <td><%= table.getString("nome") %></td>
                    <td><%= table.getString("descrizione") %></td>
                    <td><%= table.getString("quantitaDisponibile") %></td>
                    <td>
                        <%
                            if(table.getBoolean("conRicetta"))
                            {
                        %>
                        &#10004
                        <%
                            }
                            else
                            {
                        %>
                        &#10008
                        <%
                            }
                        %>
                    </td>

                    <form action="<%=request.getContextPath()%>/carrello.do" method="post" name="form">
                        <td>
                            <input type="number" name="qty" min="0" max="5">boxes<br>
                        </td>
                        <td class="blank">
                            <input type="submit" value="Add to Cart">
                            <input type="text" name="productName" id="productName" value="<%= table.getString("codProdotto") %>"
                               style="visibility:hidden">
                        </td>
                    </form>
                </tr>
                <%
                    }
                %>
            </table>

            <br>
            <form action="<%=request.getContextPath()%>/acquisto.do" method="post" name="form">
                <input type ="submit" value="Purchase">
            </form>
        </div>
        <div class="clear"/>

    </div>

    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>

<%
    String msg = (String) request.getSession().getAttribute("msg");

    if(msg != null)
    {%>
<script>
    alert("<%= msg %>");
</script>
<%}%>

</body>
</html>
