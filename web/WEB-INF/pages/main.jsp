<%-- 
    Document   : main
    Created on : Nov 20, 2014, 4:52:54 PM
    Author     : Andrew
    Description:
    Main page, containing the basic structure of the site. Other pages are
    included in here. You could consider this the template where the other
    pages are slotted in. If we didn't have this, a bunch of html would be
    duplicated across multiple pages. Ick.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${context}/css/fatal_style.css">
        <title>Fatal System</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <%@include file="/WEB-INF/jspf/show_error_bean.jspf"%>
            <%--  Get the page to include --%>
            <jsp:include page="${included}" flush="true" /> 
        </div>    
        <div class="clear"></div>
        <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    </body>


</html>
