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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.DatabaseHandler;
import models.ErrorBean;
import models.Medicine;

/**
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
        String action = request.getParameter("desiredAction");
        
        switch (action) {
            case "show_medicine":
                showAllMedicines(request, response);
            case "add_medicine":
                addMedicine(request, response);
            case "change_price":
                changeMedicinePrice(request, response);
            case "remove_medicine":
                removeMedicine(request, response);
            default:
                showAllMedicines(request, response);
        }
    }
    
    private void showAllMedicines(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        List medicines = new ArrayList<Medicine>();
        try {
            ResultSet res = dbh.executeSelect("SELECT * FROM patients");
        
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int cost = res.getInt("cost");
                
                Medicine m = new Medicine();
                m.setId(id);
                m.setName(name);
                m.setCost(cost);
                medicines.add(m);
            }
        } catch (SQLException e) {
            createErrorBean(request, e);
        }
        
        request.setAttribute("medicines", medicines);
        forward(request, response, "url");
    }
    
    private void addMedicine(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        
        String name = request.getParameter("name");
        int cost = Integer.parseInt(request.getParameter("cost"));
        
        try {
            dbh.executeUpdate("INSERT INTO medicines (name, cost) "
                    + "VALUES (" + name + ", " + cost + ")");
        } catch (SQLException e) {
            createErrorBean(request, e);
        }
        
        // forward to medicine page.
        forward(request, response, "url");
    }
    
    private void changeMedicinePrice(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        
        int id = Integer.parseInt(request.getParameter("id"));
        int newCost = Integer.parseInt(request.getParameter("newCost"));
        try {
            dbh.executeUpdate("UPDATE medicines SET cost=" + newCost + 
                    "WHERE id=" + id);
        } catch(SQLException e) {
            createErrorBean(request, e);
        }
        
        // forward to medicine page
        forward(request, response, "URL");
    }
    
    private void removeMedicine(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        DatabaseHandler dbh = new DatabaseHandler(conn);
        
        int doomedMedicineId = (int)request.getAttribute("cost");
        try {
            dbh.executeUpdate("DELETE FROM medicines WHERE id=" + doomedMedicineId);
        } catch(SQLException e) {
            createErrorBean(request, e);
        }
        
        //forward to medicine page
        forward(request, response, "URL");
    }
    
    private void createErrorBean(HttpServletRequest r, SQLException e) {
        ErrorBean eb = new ErrorBean();
        eb.setError(true);
        eb.setMessage(e.toString());
        r.setAttribute("errorBean", eb);
    }
    
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
