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
    Connection conn = (Connection) getServletContext().getAttribute("connection");
    DatabaseHandler dbh = new DatabaseHandler(conn);
    String desiredAction = (String) request.getAttribute("desired_action");
    boolean mChange = false;
    if (desiredAction.equals("add_medicine")) {
        MedicineHandler.addMedicine(dbh, request.getParameter("name"), Integer.parseInt(request.getParameter("cost")));
        mChange = true;
    } else if (desiredAction.equals("change_medicine_price")) {
        MedicineHandler.changeMedicinePrice(dbh, Integer.parseInt(request.getParameter("id")), Integer.parseInt(request.getParameter("newCost")));
        mChange = true;
    }
    List<Medicine> medicines = MedicineHandler.retrieveAllMedicines(dbh, request.getSession(), mChange);
    pageContext.setAttribute("medicines", medicines, PageContext.REQUEST_SCOPE);
%>