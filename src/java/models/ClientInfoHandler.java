/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A static class designed to both put ClientInfo objects into the database,
 * and grab them out again.
 * 
 * @author Andrew
 */
public class ClientInfoHandler {
    
    /**
     * Grab all of the information about client info from the database, and
     * place the resulting ClientInfo objects in a list. This list is then
     * returned.
     * 
     * @param dbh
     * @return
     */
    public static List<ClientInfo> retrieveClientInfos(DatabaseHandler dbh) {
        List<ClientInfo> clientInfos = new ArrayList<>();
        try {
            ResultSet res = dbh.executeSelect("SELECT * FROM client_info");
            while(res.next()) {
                ClientInfo c = new ClientInfo(res.getInt("id"), res.getString("ip"), res.getString("date"), res.getString("page"), res.getString("browser"));
                clientInfos.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientInfoHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientInfos;
    }
    
    /**
     * Add some client information into the database. Stored the ip address, the date,
     * the browser used, and the page requested.
     * 
     * @param dbh
     * @param ip
     * @param date
     * @param browser
     * @param page
     */
    public static void addClientInfos(DatabaseHandler dbh, String ip, String date, String browser, String page) {
        try {
            dbh.executeUpdate("INSERT INTO client_info (ip, date, browser, page)"
                    + "VALUES (" + quotify(ip) + ", " + quotify(date) + ", " + quotify(browser) + ", " + quotify(page) + ")");
        } catch (SQLException ex) {
            Logger.getLogger(ClientInfoHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // function intended to simplify the constructing of sql strings. Quotes the
    // given value.
    private static String quotify(String s) {
        return "'" + s + "'";
    }
}
