<%-- 
    Document   : index
    Created on : Nov 16, 2014, 3:43:28 PM
    Author     : Andrew
    Description: 
    The homepage for the website. This is really a jumping off point for the 
    rest of the site; there's not much else here otherwise. 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/fatal_style.css">
        <title>Fatal System - Home</title>
    </head>
    <body>
        <%@include file="jspf/header.jspf"%>
        <%@include file="jspf/login_validation.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <h1>Fatal System Home</h1>
            <p>Welcome Doctor Fatal.</p>
            <p>
                To get started, click on either the patient or medicine links 
                above. You will be able to view patients and medicines, and
                perform and required actions.
            </p>
        </div>    
        <div class="clear"></div>
        <%@ include file="jspf/footer.jspf"%>
    </body>


</html>