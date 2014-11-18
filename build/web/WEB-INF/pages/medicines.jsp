<%-- 
    Document   : medicines
    Created on : Nov 16, 2014, 3:51:32 PM
    Author     : Andrew
    Description:
    This shows a list of all the medicines on Dr. Fatal's list. From here, 
    the user can add medicines, remove medicines, and change medicine prices.
    
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Medicines</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <h1>Dr Fatal's Approved <s>Poisons</s> Medicines</h1>
            <c:forEach items="${medicines}" var="medicine">
                Name: ${medicine.name} <br/>
                Cost: £${medicine.cost} <br/>
            </c:forEach>
        </div>
        <div class="clear"></div>
        <%@include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
