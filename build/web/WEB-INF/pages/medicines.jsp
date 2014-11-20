<%-- 
    Document   : medicines
    Created on : Nov 16, 2014, 3:51:32 PM
    Author     : Andrew
    Description:
    This shows a list of all the medicines on Dr. Fatal's list. From here, 
    the user can add medicines and change medicine prices.
    
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/fatal_style.css">
        <title>Medicines</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <%@include file="/WEB-INF/jspf/show_error_bean.jspf"%>
            <h1>Dr Fatal's Approved <s>Poisons</s> Medicines</h1>
            <table>
                <tr><th>Id</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Change Price</th> 
                </tr>
                    
                <c:forEach items="${medicines}" var="medicine">
                    <tr>
                        <td>${medicine.id}</td>
                        <td>${medicine.name}</td>
                        <td>£${medicine.cost}</td>
                        <td>                        
                            <form name="change_price" action="MedicineController" method="POST">
                                <input type="number" name="newCost" min="0" value="0" />
                                <input type="hidden" name="id" value="${medicine.id}" />
                                <input type="hidden" name="desired_action" value="change_price" />
                                <input type="submit" value="Change Price" name="submit_price" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <h2>Add Medicine</h2>
            <form name="add_medicine" action="MedicineController" method="POST">
                Name: <input type="text" name="name" value="medicine" />
                Cost: <input type="number" name="cost" min="0" value="0"/>
                <input type="hidden" name="desired_action" value="add_medicine" />
                <input type="submit" value="Add Medicine" name="submit_medicine" />
            </form>    
        </div>
        <div class="clear"></div>
        <%@include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
