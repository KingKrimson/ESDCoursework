<%-- 
    Document   : medicine_actions
    Created on : Nov 20, 2014, 7:53:57 PM
    Author     : Andrew
    Description:
    Using the FrontController adaptatation, the models are accessed directly in
    the JSP pages, to avoid putting unneeded pressure on the FrontController. The
    FrontController can service someone else.

    I don't want to put scriptlets in the view, as I feel that would violate the
    view principle, and might confuse web designers, so these pages contain the
    necessary scriptlets.

    This page is included in the medicine page, and deals with all the actions
    the user can perform on that page.
--%>
<%@page import="java.sql.Connection"%>
<%@page import="models.Medicine"%>
<%@page import="models.MedicineHandler"%>
<%@page import="models.DatabaseHandler"%>
<%@page import="java.util.List"%>
<%
    DatabaseHandler dbh = (DatabaseHandler)getServletContext().getAttribute("dbh");
    String desiredAction = (String) request.getAttribute("desired_action");
    // mChange tracks whether the medicine list has been updated.
    // if so, the cached version is invalid, and has to be changed.
    boolean mChange = false;
    // add a medicine, or change a medicine price.
    if (desiredAction.equals("add_medicine")) {
        MedicineHandler.addMedicine(dbh, request.getParameter("name"), Integer.parseInt(request.getParameter("cost")));
        mChange = true;
    } else if (desiredAction.equals("change_medicine_price")) {
        MedicineHandler.changeMedicinePrice(dbh, Integer.parseInt(request.getParameter("id")), Integer.parseInt(request.getParameter("newCost")));
        mChange = true;
    }
    // grab a cached version of the medicine list, or get a new one and cache that one.
    List<Medicine> medicines = MedicineHandler.retrieveAllMedicines(dbh, request.getSession(), mChange);
    pageContext.setAttribute("medicines", medicines, PageContext.REQUEST_SCOPE);
%>