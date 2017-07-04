<%@ page import="util.TableReader" %>
<%@ page import="Beans.LoginBean" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Write a Mail</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
    <jsp:include page="../util/login.jsp"/>
    <%
        String role = ((String) request.getSession().getAttribute("role")).toLowerCase();
    %>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Write a new mail</h1>
    </div>

    <div id="cont">

        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>

        <div id="elenco" class="right" align="center">
            <form action="<%=request.getContextPath()%>/mail.do" method="post" name="form">
                <div class="clear">
                    <div class="tleft">Receiver Username</div>
                    <%
                        try
                        {

                            TableReader reader = new TableReader();
                            LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));
                            String userUsername = bean.getUsername();
                            ResultSet table = reader.buildNewMail(role, userUsername);
                            String username;
                            boolean reg = role.equals("reg");

                            if(role.equals("tf"))
                            {
                    %>
                            <input type="checkbox" name="username" value="regPiemonte" checked>Regione Piemonte
                    <%

                        }

                    %>
                    <div class="tright">
                        <%
                            while(table.next())
                            {
                                username = table.getString("username");
                                if(!reg && username.equals(userUsername))
                                    continue;
                        %>
                    <input type="checkbox" name="username" value="<%= username %>"><%= username %>
                <%
                        }
                    }
                        catch (Exception e)
                        { }
                    %>
                    </div>
                </div>
                <br><br>
                <div class="clear">
                    <div class="tleft">Subject</div>
                    <div class="tright"><input type="text" name="obj" id="sub" required></div>
                </div>
                <div class="clear">
                    <div class="tleft">Text</div>
                    <div class="tright"><textarea name="text" id="text" rows="10" cols="50" required></textarea></div>
                </div>
                <br>
                <br>
                    <input type="submit" value="SEND">


            </form>
        </div>
        <div class="clear"/>
    </div>
</div>
<div id= "footer">
    <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
</div>

</body>
</html>
