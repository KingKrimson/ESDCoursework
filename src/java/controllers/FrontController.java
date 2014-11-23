/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrew
 */
public class FrontController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * Essentially, this is just a router. There's a file in WEB-INF, page and
     * action mappings, that describes the actions in the links around the site,
     * and links them to a page to include in the template system, and an action
     * to take.
     * 
     * This controller retrieves the page and the action, and forwards them on
     * to the main.jsp page.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id; // process Request URL
        String page = "/WEB-INF/pages/main.jsp"; // what page to forward to ...
        String include; // page to include into main.jsp
        String action; // action to take in page.
        
        // find last part of requested resource
        id = request.getRequestURI().substring(
                request.getContextPath().length());

        getServletContext().log("Front received a request for " + id);
        // pageMappings and actionMappings are both created in the context listener.
        // they contain, respectively, the page to include, and the action to take.
        Map<String, String> pageMappings = (Map<String, String>) getServletContext()
                .getAttribute("page_mappings");
        Map<String, String> actionMappings = (Map<String, String>) getServletContext()
                .getAttribute("action_mappings");

        // grab the canonical page to include in the template.
        if (pageMappings.containsKey(id)) {
            include = pageMappings.get(id);
        } else {
            // This page is just a simple error page to include if the link is wrong.
            include = "/WEB-INF/pages/error.jsp";
        }
        // grab the action to take in the template
        if (actionMappings.containsKey(id)) {
            action = actionMappings.get(id);
        } else {
            // the action default_action tends not to do anything.
            action = "default_action";
        }
        
        // Handle login attempts, if the user is on the login page.
        if (id.equals("/pages/Login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if ("doctor".equals(username) && "doctor".equals(password)) {
                // login successful, so create a session, and set the validUser attribute.
                request.getSession().setAttribute("validUser", true);
            } else {
                // login failed, send the user back.
                request.setAttribute("loginFailed", true);
                include = "/WEB-INF/pages/login.jsp";
            }
        } else if (request.getSession().getAttribute("validUser") == null){
            // user is not logged in.
            include = "/WEB-INF/pages/login.jsp";
        }

        // forward to the main page, and specify the page to include, and the
        // desired action to take on that page.
        request.setAttribute("included", include);
        request.setAttribute("desired_action", action);
        request.getRequestDispatcher(page).forward(request, response);

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
