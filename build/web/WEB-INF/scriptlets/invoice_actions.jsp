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
    DatabaseHandler dbh = (DatabaseHandler)getServletContext().getAttribute("dbh");  
    String desiredAction = (String)request.getAttribute("desired_action");
    // Take an action if required. Either add a medicine, or pay the bill.
    if (desiredAction.equals("add_patient_medicine")) {
        String[] medicine_ids = request.getParameterValues("medicine_ids");
        int[] quantities = new int[medicine_ids.length];
        for (int i = 0; i < medicine_ids.length; ++i) {
            String id = medicine_ids[i];
            quantities[i] = Integer.parseInt(request.getParameter("medicine_quantities_" + id));
        }
        PatientHandler.addMedicines(dbh, 
                       Integer.parseInt(request.getParameter("id")), 
                       medicine_ids,
                       quantities);
    } else if (desiredAction.equals("pay_invoice")) {
       PatientHandler.payBill(dbh, Integer.parseInt(request.getParameter("id")));
    }
    // For the invoice, we always need a patient, so grab the patient info.
    Patient p = PatientHandler.retrievePatient(dbh, 
            Integer.parseInt(request.getParameter("id")));
    // Grab either a new medicine list, or a cached copy if one exists.
    List<Medicine> medicines = MedicineHandler.retrieveAllMedicines(dbh, request.getSession(), false);
    pageContext.setAttribute("patient", p, PageContext.REQUEST_SCOPE);
    pageContext.setAttribute("medicines", medicines, PageContext.REQUEST_SCOPE);
%>