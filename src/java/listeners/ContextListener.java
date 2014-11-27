package listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import models.DatabaseHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Web application lifecycle listener.
 *
 * Grabs the datasource string from the DD, and creates a database connection.
 * Additionally, creates a pageMap and actionMap object, using the file 
 * 'page_and_action_mappings.' That file contains links provided on the forms
 * in the website, and links them to a canonical file, and an action that should
 * be taken on that file.
 * 
 * The resulting connection, pageMap and actionMap are stored in the servelet 
 * context, so that they're available across the entire application.
 *
 * @author Andrew
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String datasource = sce.getServletContext().getInitParameter("datasource");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(datasource, "root", "");
            // store the connection in the servlet context.
            //sce.getServletContext().setAttribute("connection", conn);
            DatabaseHandler dbh = new DatabaseHandler(conn);
            // store the database handle in the context.
            sce.getServletContext().setAttribute("dbh", dbh);
            
            // read this file, and place the information into two maps.
            String mappingFile = "/WEB-INF/page_and_action_mappings";
            Scanner scan = new Scanner(new File(sce.getServletContext().getRealPath(mappingFile)));
            Map<String, String> pageMap = new HashMap<>();
            Map<String, String> actionMap = new HashMap<>();

            // skip explanation comment in the mapping file.
            scan.nextLine();
            while (scan.hasNextLine()) {
                // split the line on spaces. Format is 'link' 'actual_file' 'action'
                String line = scan.nextLine();
                String[] words = line.split(" +");

                pageMap.put(words[0], words[1]);
                actionMap.put(words[0], words[2]);
            }
            // save both the maps.
            sce.getServletContext().setAttribute("page_mappings", pageMap);
            sce.getServletContext().setAttribute("action_mappings", actionMap);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
