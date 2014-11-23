<%-- 
    Document   : web_services
    Created on : Nov 19, 2014, 8:25:42 PM
    Author     : Andrew
    Description: 
    The page which deals with web services. Specifically, an arbitrary spelling
    example. Allows the user to enter text. Once the text is submitted, the 
    web service goes off and checks the spelling. The model replaces the misspelled
    words with correct ones, and displays the text back to the user.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<%@page import="models.WebServiceSpellingChecker"%>
<%
    String desiredAction = (String) request.getAttribute("desired_action");
    if (desiredAction.equals("check_spelling")) {
        String replacedText = WebServiceSpellingChecker.checkAndFixSpelling(request.getParameter("TextArea1"));
        pageContext.setAttribute("replaced_text", replacedText);
    }
%>
<h1>Web Services Spell Checker</h1>
<p>Service being used is the spell checker from the CDYNE Corporation.</p>
<form name="Test" method="post" action="${context}/pages/CheckSpelling">
    <p>Enter the text you want to check:</p>
    <p />
    <p>
        <textarea rows="7" name="TextArea1" cols="40" ID="Textarea1" >${replaced_text}</textarea>
    </p>
    <p />
    <input type="submit" value="Spell Check" name="spellcheckbutton">
</form>