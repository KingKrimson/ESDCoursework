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
<c:set var="context" value="${pageContext.request.contextPath}" />
<!-- Include the scriptlet that calls the model -->
<jsp:include page="/WEB-INF/scriptlets/patient_actions.jsp" flush="true" />
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

    <!-- Show each patient, along with mechanisms for creating an invoice for them, and deleting them -->
    <c:forEach items="${patients}" var="patient">
        <tr>
            <td>${patient.id}</td>
            <td>${patient.name}</td>
            <td>                        
                <form name="invoice_patient" action="${context}/pages/ShowInvoice" method="GET">
                    <input type="hidden" name="id" value="${patient.id}" />
                    <input type="submit" value="View/Modify Invoice" name="submit_invoice" />
                </form>
            </td>
            <td>
                <form name="remove_patient" action="${context}/pages/RemovePatient" method="POST">
                    <input type="hidden" name="id" value="${patient.id}" />
                    <input type="hidden" name="name" value="${patient.name}" />
                    <input type="submit" value="Remove Patient" name="submit_remove" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<!-- Form to add a new patient, along with a consultation fee and a list of medicines. -->
<h2>Add Patient</h2>
<form name="add_patient" action="${context}/pages/AddPatient" method="POST">
    Name: <input type="text" name="name" value="Hugh Mann"></br>
    Consultation Fee: <input type="number" name="fee" min="0" value="0" /></br>
    <c:forEach items="${medicines}" var="medicine">
        <input type="checkbox" name="medicine_ids" value="${medicine.id}" />
        <input type="number" name="medicine_quantities_${medicine.id}" value="1" min="0" />
        ${medicine.name} (£${medicine.cost})<br/>
    </c:forEach>
    <input type="hidden" name="desired_action" value="add_patient" />        
    <input type="submit" value="Add Patient" name="add_patient" />
</form>
