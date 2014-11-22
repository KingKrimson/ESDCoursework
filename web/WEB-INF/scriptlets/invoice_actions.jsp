<%-- 
    Document   : invoice_actions
    Created on : Nov 20, 2014, 7:52:50 PM
    Author     : Andrew
    Description:
    Using the FrontController adaptatation, the models are accessed directly in
    the JSP pages, to avoid putting unneeded pressure on the FrontController. The
    FrontController can service someone else.

    I don't want to put scriptlets in the view, as I feel that would violate the
    view principle, and might confuse web designers, so these pages contain the
    necessary scriptlets.

    This page is included in the patients page, and deals with all the actions
    the user can perform on that page.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="models.Medicine"%>
<%@page import="models.Patient"%>
<%@page import="java.util.List"%>
<%@page import="models.MedicineHandler"%>
<%@page import="models.PatientHandler"%>
<%@page import="models.DatabaseHandler"%>
<%@page import="java.sql.Connection"%>
<%
    Connection conn = (Connection)getServletContext().getAttribute("connection");
    DatabaseHandler dbh = new DatabaseHandler(conn);  
    String desiredAction = (String)request.getAttribute("desired_action");
    if (desiredAction.equals("add_patient_medicine")) {
         PatientHandler.addMedicines(dbh, 
                        Integer.parseInt(request.getParameter("id")), 
                        request.getParameterValues("medicine_ids"));
    } else if (desiredAction.equals("pay_invoice")) {
       PatientHandler.payBill(dbh, Integer.parseInt(request.getParameter("id")));
    }
    Patient p = PatientHandler.retrievePatient(dbh, 
            Integer.parseInt(request.getParameter("id")));
    List<Medicine> medicines = MedicineHandler.retrieveAllMedicines(dbh, request.getSession(), false);
    pageContext.setAttribute("patient", p, PageContext.REQUEST_SCOPE);
    pageContext.setAttribute("medicines", medicines, PageContext.REQUEST_SCOPE);
%>