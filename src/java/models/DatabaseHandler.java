/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Andrew
 */
public class DatabaseHandler {
    Connection conn;
    
    public DatabaseHandler(Connection conn) {
        this.conn = conn;
    }
    
    public int executeUpdate(String query) 
            throws SQLException {
        Statement stmt = conn.createStatement();    
        int status = stmt.executeUpdate(query);
        return status;
    }
    
    public int executeInsert(String query)
            throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int status = stmt.executeUpdate();
        int key = 0;
        
        if (status != 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    key = generatedKeys.getInt(1);
                }
            }
        }
        return key;
    }
    
    public ResultSet executeSelect(String query) 
        throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet res = stmt.getResultSet();
        return res;
    }
    
}
