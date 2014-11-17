<%-- 
    Document   : login
    Created on : Nov 16, 2014, 4:33:36 PM
    Author     : Andrew
    Description:
    This is the login page. The user is redirected here if they're not logged in.
    True to it's name, it allows the user to login.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Medicines</title>
    </head>
    
    <%-- Redirect to homepage if session is valid? --%>
    
    <body>
        <%@include file="jspf/header.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <h1>Login</h1>
            <p>Please login to access the system.</p>
            echo "<div class=login> <!-- start of login -->\n";
                <form class='login' action='login_handler' method='post'>
                    <table class='login'>
                        <tr>
                            <td><input type='text' id='Name' name='username' placeholder='Username' size='16'/></td>
                            <td><input type='password' id='Password' name='password' placeholder='Password' size='16'/></td>
                            <td><input type='submit' name='login' value='login' /></td>
                        </tr>
                    </table>
                </form>
            </div>  <!-- End of login -->

        </div>
        <div class="clear"></div>
        <%@include file="jspf/footer.jspf"%>
    </body>
</html>
