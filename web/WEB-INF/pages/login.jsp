<%-- 
    Document   : login
    Created on : Nov 16, 2014, 4:33:36 PM
    Author     : Andrew
    Description:
    This is the login page. The user is redirected here if they're not logged in.
    True to it's name, it allows the user to login.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<h1>Login</h1>
<c:if test="${loginFailed}">
    Login Failed! Please try again.
</c:if>
<p>Please login to access the system.</p>

<div class=login> <!-- start of login -->
    <form class='login' action='${context}/pages/Login' method='post'>
        <table class='login'>
            <tr>
                <td><input type='text' id='Name' name='username' placeholder='Username' size='16'/></td>
                <td><input type='password' id='Password' name='password' placeholder='Password' size='16'/></td>
                <td><input type='submit' name='login' value='login' /></td>
            </tr>
        </table>
    </form>
</div>  <!-- End of login -->

