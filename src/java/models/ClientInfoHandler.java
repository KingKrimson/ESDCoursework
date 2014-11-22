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
 *
 * @author Andrew
 */
public class ClientInfoHandler {
    
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
    
    public static void addClientInfos(DatabaseHandler dbh, String ip, String date, String browser, String page) {
        try {
            dbh.executeUpdate("INSERT INTO client_info (ip, date, browser, page)"
                    + "VALUES (" + quotify(ip) + ", " + quotify(date) + ", " + quotify(browser) + ", " + quotify(page) + ")");
        } catch (SQLException ex) {
            Logger.getLogger(ClientInfoHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String quotify(String s) {
        return "'" + s + "'";
    }
}
