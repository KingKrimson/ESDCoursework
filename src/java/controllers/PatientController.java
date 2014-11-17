/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.DatabaseHandler;
import models.Patient;

/**
 *
 * @author Andrew
 */
public class PatientController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("desiredAction");
        
        switch (action) {
            case "show_patients":
                showAllPatients(request, response);
            case "add_patient":
                addPatient(request, response);
            case "remove":
                removePatient(request, response);
            case "invoice":
                createInvoice(request, response);
            case "add_medicine":
                addMedicine(request, response);
            case "pay":
                payBill(request, response);
            default:
                showAllPatients(request, response);
        }
    }
    
    private void showAllPatients(HttpServletRequest request, HttpServletResponse response) {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        List patients = new ArrayList<Patient>();
        try {
            ResultSet res = dbh.executeSelect("SELECT * FROM patients");
        
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                Patient p = new Patient();
                p.setId(id);
                p.setName(name);
                patients.add(p);
            }
        } catch (SQLException e) {
            request.setAttribute("isError", true);
        }
        
        request.setAttribute("patients", patients);
        forward(request, response, "url");
    }
    
    private void addPatient(HttpServletRequest request, HttpServletResponse response) {
        //remember to add consultation fee...
        Connection con = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(con);
        
        String name = request.getParameter("name");
        int consultationFee = Integer.parseInt(request.getParameter("fee"));
        String[] medicineIds = request.getParameterValues("medicineIds");
        
        try {
            int patientId = dbh.executeInsert("INSERT INTO patients (name) VALUES (" + name + ")");
            
            for (String medicineId : medicineIds) {
                dbh.executeInsert("INSERT INTO patient_medicines (patient_id, medicine_id)"
                        + "VALUES (" + patientId + ", " + medicineId + ")");
            }
            
            dbh.executeInsert("INSERT INTO patient_consultations (patient_id, cost) "
                    + "VALUES (" + patientId + ", " + consultationFee + ")");
        } catch (SQLException e) {
            request.setAttribute("isError", true);
            request.setAttribute("errorMessage", e.toString());
        }
        
        forward(request, response, "url");
    }
    
    private void removePatient(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, "url");
    }
    
    private void createInvoice(HttpServletRequest request, HttpServletResponse response) {
        //show consultation fee
        
        // forward to invoice page
        forward(request, response, "url");
    }
    
    private void addMedicine(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, "url");
    }
    
    private void payBill(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, "url");
    }
    
    private void forward(HttpServletRequest request, HttpServletResponse response, String url) {
  
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
