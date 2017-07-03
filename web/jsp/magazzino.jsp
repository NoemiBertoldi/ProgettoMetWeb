<%@ page import="Beans.LoginBean" %>
<%@ page import="util.TableReader" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WareHouse</title>
    <jsp:include page="../util/login.jsp"/>
    <% String role = (String) request.getSession().getAttribute("role"); %>
</head>
<body>
<div id="container">
    <div id="header">
        <%
            if(((String) request.getSession().getAttribute("role")).toLowerCase().equals("tf"))
            {
        %>
        <h1>Warehouse Management</h1>
        <%
            }
            else
            {
        %>
        <h1>Warehouse Product List</h1>
        <%
            }
        %>
    </div>
    <div id="cont">

        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>
        <div id="elenco" class="right">
            <table>
            <tr>
                <th>Product Name</th>
                <th>Description</th>
                <th>Available Quantity </th>
                <th>Prescription</th>
            <%
                if(role.toLowerCase().equals("tf"))
                {
            %>
                <th>Quantity</th>
                <th>Order</th>
            <%
                }
            %>
            </tr>
            <%
                TableReader reader = new TableReader();
                LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));

                ResultSet table = reader.buildWarehouseTable(bean.getUsername());

                while(table.next())
                {
            %>
            <tr>
                <td><%= table.getString("nome") %></td>
                <td><%= table.getString("descrizione") %></td>
                <td><%= table.getString("quantitaDisponibile") %></td>
                <td><%= table.getBoolean("conRicetta") %><%
                    if(table.getBoolean("conRicetta"))
                    {
                %>
                    &#10004;
                    <%
                    }
                    else
                    {
                    %>
                    &#10008;
                    <%
                        }
                    %>
                </td>
                <%
                    if(role.toLowerCase().equals("tf"))
                    {
                %>
                    <form action="<%=request.getContextPath()%>/riempiMag.do" method="post" name="form">
                <td>
                    <input type="number" name="qty" id="qty" min="1" max="50">
                </td>
                <td>
                    <input type="submit" value="Order">
                    <input type="text" name="nomeprodotto" id="nomeprodotto" value="<%= table.getString("nome") %>"
                       style="visibility:hidden">
                </td>
                    </form>
                <%
                    }
                %>
            </tr>
            <%
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
