/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.MedicineHandler;
import models.PatientHandler;
import models.DatabaseHandler;
import models.ErrorBean;
import models.Medicine;
import models.Patient;

/**
     * All patient specific requests are handed to this Servlet. It can show
     * all the patients, add patients to the list, remove patients from the
     * list, add medicines to the patient's medicine list, and pay the patients
     * bills.
     *
     * @author Andrew
     */
public class PatientController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * Entry point to the servlet.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This parameter tells the servlet what to do.
        String action = request.getParameter("desired_action");

        // If there's no action, we just want to see all the patients.
        if (action == null) {
            showAllPatients(request, response);
        }

        // Perform the action indicated by the parameters.
        switch (action) {
            case "show_patients":
                showAllPatients(request, response);
                break;
            case "add_patient":
                addPatient(request, response);
                break;
            case "remove_patient":
                removePatient(request, response);
                break;
            case "invoice_patient":
                createInvoice(request, response);
                break;
            case "add_medicine":
                addMedicine(request, response);
                break;
            case "pay_invoice":
                payBill(request, response);
                break;
            default:
                showAllPatients(request, response);
        }
    }

    /*
     * Retrieves the patients (except for the medicines and consultation fees) 
     * from the database, put them into a list, and passes them onto the jsp.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void showAllPatients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        List<Patient> patients = new ArrayList<>();
        try {
            patients = PatientHandler.retrieveAllPatients(dbh);
            getAllMedicines(dbh, request);
        } catch (SQLException e) {
            createErrorBean(request, e.getMessage());
        }

        // allows the jsp to access the patient list.
        request.setAttribute("patients", patients);
        // forward to the patient page.
        forward(request, response, "/WEB-INF/pages/patients.jsp");
    }

    /*
     * Adds a patient, including their consultation fee and initial medicine list,
     * to the database.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void addPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = (Connection) getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(con);

        String name = request.getParameter("name");
        int consultationFee = Integer.parseInt(request.getParameter("fee"));
        String[] medicineIds = request.getParameterValues("medicine_ids");

        try {
            PatientHandler.addPatient(dbh, name, medicineIds, consultationFee);
        } catch (SQLException e) {
            createErrorBean(request, e.getMessage());
        }

        showAllPatients(request, response);
    }
    
     /*
     * Removes a patient from the database, assuming that they've paid all their
     * bills. If they haven't, an attribute is set to notify the user.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void removePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = (Connection) getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(con);
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        try {
            Patient p = PatientHandler.removePatient(dbh, id, name);
            // if p isn't null, then the patient hasn't been removed.
            if (p != null) {
                request.setAttribute("notRemoved", "Couldn't delete " + p.getName()
                        + ": They still have to pay their bill of £" + p.getTotalFee() + "!");
            }

        } catch (SQLException e) {
            createErrorBean(request, e.getMessage());
        }

        // forward to patients
        showAllPatients(request, response);
    }

    /*
     * Collects all of the patients information, and passes it to the invoice
     * page, where it can be displayed.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void createInvoice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = (Connection) getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(con);

        getAllMedicines(dbh, request);

        Patient p = new Patient();

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            p = PatientHandler.retrievePatient(dbh, id);
            request.setAttribute("patient", p);
        } catch (SQLException e) {
            createErrorBean(request, e.getMessage());
            // An error has occured, so forward to patient page instead of invoice page.
            showAllPatients(request, response);
        }

        // forward to invoice page
        forward(request, response, "/WEB-INF/pages/invoice.jsp");
    }

    /*
     * Adds a set of medicines to the patients medicine list.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void addMedicine(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = (Connection) getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(con);
        int id = Integer.parseInt(request.getParameter("id"));
        String[] medicineIds = request.getParameterValues("medicine_ids");

        try {
            PatientHandler.addMedicines(dbh, id, medicineIds);
        } catch (SQLException e) {
            createErrorBean(request, e.getMessage());
        }
        // forward to the invoice page, which should have the new medicines.
        createInvoice(request, response);
    }

    /*
     * Pays the patient's bills.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void payBill(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = (Connection) getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(con);
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            PatientHandler.payBill(dbh, id);
        } catch (SQLException e) {
            createErrorBean(request, e.getMessage());
        }

        // forward to invoice page, which should now be empty.
        createInvoice(request, response);
    }

    /*
     * Get all medicines from the database. Some patient pages need this information,
     * as they allow the user to add medicines to the bill, or add patients.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void getAllMedicines(DatabaseHandler dbh, HttpServletRequest request) {
        List<Medicine> medicines = new ArrayList<>();
        try {
            medicines = MedicineHandler.retrieveAllMedicines(dbh);
        } catch (SQLException e) {
            createErrorBean(request, e.toString());
        }

        request.setAttribute("globalMedicines", medicines);
    }

    // Creates an ErrorBean, so the jsp can display any errors.
    private void createErrorBean(HttpServletRequest r, String m) {
        ErrorBean eb = new ErrorBean();
        eb.setError(true);
        eb.setMessage(m);
        r.setAttribute("errorBean", eb);
    }
    
    // Wrapper for the forward functionality.
    private void forward(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        RequestDispatcher dispatch = request.getRequestDispatcher(url);
        dispatch.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
