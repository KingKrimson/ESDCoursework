<%-- 
    Document   : patients
    Created on : Nov 16, 2014, 3:51:54 PM
    Author     : Andrew
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patients</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <h1>Dr Fatal's Patients</h1>
            <c:forEach items="${patients}" var="patient">
                Name: ${patient.name} <br>
                Id: ${patient.id}<br>
            </c:forEach>
        </div>
        <div class="clear"></div>
        <%@include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
