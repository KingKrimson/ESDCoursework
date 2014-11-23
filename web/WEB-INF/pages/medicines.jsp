<%-- 
    Document   : medicines
    Created on : Nov 16, 2014, 3:51:32 PM
    Author     : Andrew
    Description:
    This shows a list of all the medicines on Dr. Fatal's list. From here, 
    the user can add medicines and change medicine prices.
    
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="/WEB-INF/scriptlets/medicine_actions.jsp" flush="true" />
<!-- Include the scriptlet that calls the model -->
<h1>Dr Fatal's Approved Medicines</h1>
<table>
    <tr><th>Id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Change Price</th> 
    </tr>
    
    <!-- Display each medicine, along with a mechanism for changing their price. -->
    <c:forEach items="${medicines}" var="medicine">
        <tr>
            <td>${medicine.id}</td>
            <td>${medicine.name}</td>
            <td>£${medicine.cost}</td>
            <td>                        
                <form name="change_price" action="${context}/pages/ChangeMedicinePrice" method="POST">
                    <input type="number" name="newCost" min="0" value="0" />
                    <input type="hidden" name="id" value="${medicine.id}" />
                    <input type="submit" value="Change Price" name="submit_price" />
                </form>
            </td>
        </tr>
    </c:forEach>
    </table>
    <!-- The form that allows the user to add a new medicine. -->
    <h2>Add Medicine</h2>
    <form name="add_medicine" action="${context}/pages/AddMedicine" method="POST">
        Name: <input type="text" name="name" value="medicine" />
        Cost: <input type="number" name="cost" min="0" value="0"/>
        <input type="submit" value="Add Medicine" name="submit_medicine" />
    </form>    

