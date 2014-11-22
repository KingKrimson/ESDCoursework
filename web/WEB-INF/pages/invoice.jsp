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
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="/WEB-INF/scriptlets/invoice_actions.jsp" flush="true" />
<h1>Invoice for ${patient.name}</h1>
<h2>Medicines</h2>
<c:forEach items="${patient.medicines}" var="medicine">
    <p>${medicine.name}: �${medicine.cost}</p>
</c:forEach>

<h2>Consultation Fee</h2>
<p>Consultation Fee: �${patient.consultationFee}</p>
<h2>Total Fee</h2>
<p>Total Fee: �${patient.totalFee}</p>
<form name="pay_invoice" action="${context}/pages/PayInvoice" method="POST">
    <input type="hidden" name="id" value="${patient.id}" />
    <input name="pay_invoice" type="submit" value="Pay Bill" />
</form>
<hr />
<h2>Add Medicine</h2>
<form name="add_medicine" action="${context}/pages/AddPatientMedicine" method="POST">
    <c:forEach items="${medicines}" var="medicine">
        <input type="checkbox" name="medicine_ids" value="${medicine.id}" />
        ${medicine.name} (�${medicine.cost})<br/>
    </c:forEach>
    <input type="hidden" name="id" value="${patient.id}" />       
    <input type="submit" value="Add Selected Medicines" name="add_medicine" />
</form>

