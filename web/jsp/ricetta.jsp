<%@ page import="util.TableReader" %>
<%@ page import="Beans.LoginBean" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prescription</title>
    <jsp:include page="../util/login.jsp"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/javascript/val_ricetta.js"></script>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Prescription Drug</h1>
    </div>

    <div id="cont">
        <div id="left" class="left">
            <jsp:include page="../util/menu.jsp"/>
        </div>
        <div id="elenco" class="right">
            <form action="<%=request.getContextPath()%>/prescription.do" method="post" name="form" onsubmit="return validatePrescriptionForm()">
                <div class="clear">
                    <div class="fleft">
                        <h4>Patient's Data: </h4>
                        <div class="clear">
                            <div class="tleft">Fiscal Code</div>
                            <div class="tright"><input type="text" name="cfPaz" id="cfPaz" required></div>
                        </div>
                        <div class="clear">
                            <div class="tleft">Name</div>
                            <div class="tright"><input type="text" name="nomePaz" id="nomePaz" required></div>
                        </div>
                        <div class="clear">
                            <div class="tleft">Surname</div>
                            <div class="tright"><input type="text" name="cognomePaz" id="cognomePaz" required></div>
                        </div>
                        <div class="clear">
                            <div class="tleft">Birth Date</div>
                            <div class="tright"><input type="text" name="dataNascitaPaz" id="dataNscitaPaz" required></div>
                        </div>
                    </div>
                    <div class="fright">
                        <h4>Prescription Data:</h4>
                        <div class="clear">
                            <div class="tleft">Doctor's Regional Code</div>
                            <div class="tright">
                                <select name="codRegMed" id="codRegMed" required>
                                <%
                                    TableReader reader = new TableReader();
                                    LoginBean bean = ((LoginBean) session.getAttribute("LoginBean"));

                                    ResultSet table = reader.buildMedTable();
                                    while(table.next())
                                    {
                                %>
                                <option value="<%=table.getString("codreg")%>"><%=table.getString("name")%> <%=table.getString("surname")%></option>
                                <%
                                    }
                                %>
                            </div>
                        </div>

                        <input type="submit" value="Record">
                    </div>
                </div>
            </form>
        </div>
        <div class="clear"/>
    </div>


    <div id= "footer">
        <h6>Created by Noemi Bertoldi - All rights reserved - 2017</h6>
    </div>
</div>
</body>
</html>
