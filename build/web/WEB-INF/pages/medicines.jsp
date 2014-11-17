<%-- 
    Document   : medicines
    Created on : Nov 16, 2014, 3:51:32 PM
    Author     : Andrew
    Description:
    This shows a list of all the medicines on Dr. Fatal's list. From here, 
    the user can add medicines, remove medicines, and change medicine prices.
    
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Medicines</title>
    </head>
    <body>
        <%@include file="jspf/header.jspf"%>
        <%@include file="jspf/login_validation.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <h1>Dr Fatal's Approved <s>Poisons</s> Medicines</h1>
            <%-- Body goes here --%>
        </div>
        <div class="clear"></div>
        <%@include file="jspf/footer.jspf"%>
    </body>
</html>
