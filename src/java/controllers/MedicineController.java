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
import models.DatabaseHandler;
import models.ErrorBean;
import models.Medicine;

/**
 * All medicine specific requests are handed to this Servlet. It can show all the
 * medicines, add medicines to the list, remove medicines from the list, and
 * change medicine prices.
 * 
 * @author Andrew
 */
public class MedicineController extends HttpServlet {

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
        // This parameter tells the servlet what to do.
        String action = request.getParameter("desired_action");
        
        // If there's no action, we just want to see all of the medicines.
        if (action == null){
            showAllMedicines(request, response);
        }
        
        // Determine what action to take.
        switch (action) {
            case "show_medicine":
                showAllMedicines(request, response);
                break;
            case "add_medicine":
                addMedicine(request, response);
                break;
            case "change_price":
                changeMedicinePrice(request, response);
                break;
            case "remove_medicine":
                removeMedicine(request, response);
                break;
            default:
                showAllMedicines(request, response);
        }
    }
    
    /*
     * Retrieves the medicines from the database, put them into a list, and
     * pass them onto the jsp.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void showAllMedicines(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        List medicines = new ArrayList<Medicine>();
        try {
            medicines = MedicineHandler.retrieveAllMedicines(dbh);
        } catch (SQLException e) {
            createErrorBean(request, e.toString());
        }
        
        // Allow the jsp to access the list.
        request.setAttribute("medicines", medicines);
        // Forward to the jsp.
        forward(request, response, "/WEB-INF/pages/medicines.jsp");
    }
    
    /*
     * Adds a medicine to the database.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void addMedicine(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        
        String name = request.getParameter("name");
        int cost = Integer.parseInt(request.getParameter("cost"));
        
        try {
            MedicineHandler.addMedicine(dbh, name, cost);
        } catch (SQLException e) {
            createErrorBean(request, e.toString());
        }
        
        // forward to medicine page.
        showAllMedicines(request, response);
    }
    
    /*
     * Changes a medicine's price in the database.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void changeMedicinePrice(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        
        int id = Integer.parseInt(request.getParameter("id"));
        int newCost = Integer.parseInt(request.getParameter("newCost"));
        try {
            MedicineHandler.changeMedicinePrice(dbh, id, newCost);
        } catch(SQLException e) {
            createErrorBean(request, e.toString());
        }
        
        // forward to medicine page
        showAllMedicines(request, response);
    }
    
    /*
     * Removes a medicine from the database. Unlikely to be used in the current
     * system.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void removeMedicine(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        
        int doomedMedicineId = (int)request.getAttribute("id");
        try {
            MedicineHandler.removeMedicine(dbh, doomedMedicineId);
        } catch(SQLException e) {
            createErrorBean(request, e.toString());
        }
        
        //forward to medicine page
        showAllMedicines(request, response);
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
