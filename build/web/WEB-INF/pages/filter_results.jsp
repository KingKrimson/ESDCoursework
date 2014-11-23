<%-- 
    Document   : filter_results
    Created on : Nov 20, 2014, 10:26:44 PM
    Author     : Andrew
    Description:
    Simply calls the client info handler to get a list of client infos, and 
    displays the results.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="models.ClientInfoHandler"%>
<%@page import="models.DatabaseHandler"%>
<%@page import="java.sql.Connection"%>
<%@page import="models.ClientInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
    Connection conn = (Connection)getServletContext().getAttribute("connection");
    DatabaseHandler dbh = new DatabaseHandler(conn);  
    List<ClientInfo> clientInfos = ClientInfoHandler.retrieveClientInfos(dbh);
    pageContext.setAttribute("client_infos", clientInfos);
%>
<h1>Client Information Grabbed by Filter</h1>
<c:forEach items="${client_infos}" var="client_info">
    <p>${client_info.id}. Request from ${client_info.ip} requesting ${client_info.page} received at ${client_info.date}. 
        Client browser is: ${client_info.browser}</p>
</c:forEach>
