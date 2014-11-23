<%-- 
    Document   : patient_actions
    Created on : Nov 20, 2014, 7:53:36 PM
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
<%@page import="models.Medicine"%>
<%@page import="models.Patient"%>
<%@page import="java.util.List"%>
<%@page import="models.MedicineHandler"%>
<%@page import="models.PatientHandler"%>
<%@page import="models.DatabaseHandler"%>
<%@page import="java.sql.Connection"%>
<%
    Connection conn = (Connection) getServletContext().getAttribute("connection");
    DatabaseHandler dbh = new DatabaseHandler(conn);
    String desiredAction = (String) request.getAttribute("desired_action");
    // pChange tracks whether the patient list has been updated.
    // if so, the cached version is invalid, and has to be changed.
    boolean pChange = false;
    // add or remove a patient.
    if (desiredAction.equals("add_patient")) {
        PatientHandler.addPatient(dbh,
                request.getParameter("name"),
                request.getParameterValues("medicine_ids"),
                Integer.parseInt(request.getParameter("fee")));
        pChange = true;
    } else if (desiredAction.equals("remove_patient")) {
        boolean removed = PatientHandler.removePatient(dbh,
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"));
        if (!removed) {
            // create an error meddage to display to the user. 
            pageContext.setAttribute("notRemoved",
                    request.getParameter("name") + " was not removed. They still need to pay their bill!");
        } else {
            pChange = true;
        }
    }

    // grab a cached version of the patient or medicine list, or get a new ones and cache those.
    List<Patient> patients = PatientHandler.retrieveAllPatients(dbh, request.getSession(), pChange);
    List<Medicine> medicines = MedicineHandler.retrieveAllMedicines(dbh, request.getSession(), false);
    pageContext.setAttribute("patients", patients);
    pageContext.setAttribute("medicines", medicines);
%>