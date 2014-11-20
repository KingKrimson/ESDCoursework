<%-- 
    Document   : invoice
    Created on : Nov 17, 2014, 4:44:46 PM
    Author     : Andrew
    Description:
    Given a patient, this page created an invoice for them , using the medicines
    that they have bought, and their consultation fee (if any). From here, the
    user can also add medicines to the bill, pay the bill, and attempt to
    remove the patient.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/fatal_style.css">
        <title>Patient Invoice</title>
    </head>
    <body>
        <%@include file="jspf/header.jspf"%>
        <div class="clear"></div>
        <div id="pagecontent">
            <%@include file="/WEB-INF/jspf/show_error_bean.jspf"%>
            <h1>Invoice for ${patient.name}</h1>
            <h2>Medicines</h2>
            <c:if test="${not empty patient.medicines}">
                <c:forEach items="${patient.medicines}" var="medicine">
                    <p>${medicine.name}: £${medicine.cost}</p>
                </c:forEach>
            </c:if>
            <h2>Consultation Fee</h2>
            <c:if test="${patient.consultationFee} > 0">    
                <p>Consultation Fee: £${patient.consultationFee}</p>
            </c:if>
            <h2>Total Fee</h2>
            <p>Total Fee: ${patient.totalFee}</p>
            <hr />
            <h2>Add Medicine</h2>
            <form name="add_medicine" action="PatientController" method="POST">
                <c:forEach items="${globalMedicines}" var="medicine">
                    <input type="checkbox" name="medicine_ids" value="${medicine.id}" />
                    ${medicine.name} (£${medicine.cost})<br/>
                </c:forEach>
                <input type="hidden" name="desired_action" value="add_medicine" />        
                <input type="submit" value="Add Selected Medicines" name="add_medicine" />
            </form>
        </div>
        <div class="clear"></div>
        <%@include file="jspf/footer.jspf"%>
    </body>
</html>
