<%-- 
    Document   : invoice
    Created on : Nov 17, 2014, 4:44:46 PM
    Author     : Andrew
    Description:
    Given a patient, this page created an invoice for them , using the medicines
    that they have bought, and their consultation fee (if any).
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patients</title>
    </head>
    <body>
        <%@include file="jspf/header.jspf"%>
        <%@include file="jspf/login_validation.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <h1>Invoice for ${patient.name}</h1>
            <%-- Body goes here --%>
        </div>
        <div class="clear"></div>
        <%@include file="jspf/footer.jspf"%>
    </body>
</html>
