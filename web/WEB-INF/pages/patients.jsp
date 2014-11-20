<%-- 
    Document   : patients
    Created on : Nov 16, 2014, 3:51:54 PM
    Author     : Andrew
    Decription : 
    This shows a list of all the patients on Dr. Fatal's list. From here, you
    can also click on a specific patient button to view that patient's invoice.
    You can also add new patients.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/fatal_style.css">
        <title>Patients</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <%@include file="/WEB-INF/jspf/show_error_bean.jspf"%>
            <h1>Dr Fatal's Patients</h1>
            <c:if test="${not empty notRemoved}">
                <p>${notRemoved}</p>
            </c:if>
            <table>
                <tr><th>Id</th>
                    <th>Name</th>
                    <th>View Invoice</th>
                    <th>Delete Patient</th> 
                </tr>
                    
                <c:forEach items="${patients}" var="patient">
                    <tr>
                        <td>${patient.id}</td>
                        <td>${patient.name}</td>
                        <td>                        
                            <form name="invoice_patient" action="PatientController" method="GET">
                                <input type="hidden" name="desired_action" value="invoice_patient" />
                                <input type="hidden" name="id" value="${patient.id}" />
                                <input type="submit" value="View/Modify Invoice" name="submit_invoice" />
                            </form>
                        </td>
                        <td>
                            <form name="remove_patient" action="PatientController" method="POST">
                                <input type="hidden" name="desired_action" value="remove_patient" />
                                <input type="hidden" name="id" value="${patient.id}" />
                                <input type="hidden" name="name" value="${patient.name}" />
                                <input type="submit" value="Remove Patient" name="submit_remove" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <h2>Add Patient</h2>
            <form name="add_patient" action="PatientController" method="POST">
                Name: <input type="text" name="name" value="Hugh Mann"></br>
                Consultation Fee: <input type="number" name="fee" min="0" value="0" /></br>
                <c:forEach items="${globalMedicines}" var="medicine">
                    <input type="checkbox" name="medicine_ids" value="${medicine.id}" />
                    ${medicine.name} (£${medicine.cost})<br/>
                </c:forEach>
                <input type="hidden" name="desired_action" value="add_patient" />        
                <input type="submit" value="Add Patient" name="add_patient" />
            </form>
        </div>
        <div class="clear"></div>
        <%@include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
