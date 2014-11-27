/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DatabaseHandler deals with the database. It can do Selects, Updates, Inserts,
 * and Deletes. It's much nicer doing database stuff in the models in rather than 
 * right in the controller. That way, if we need to port the system to a GUI
 * (or another view type), we have less work to do.
 * 
 * @author Andrew
 */
public class DatabaseHandler {
    Connection conn;
    
    /**
     * Create a database handle with the given Connection.
     * 
     * @param conn
     */
    public DatabaseHandler(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Perform an update. The query parameter is an SQL update or delete command.
     * 
     * @param query
     * @return
     * @throws SQLException
     */
    public int executeUpdate(String query) 
            throws SQLException {
        Statement stmt = conn.createStatement();    
        int status = stmt.executeUpdate(query);
        return status;
    }
    
    /**
     * Same as executeUpdate, except a prepared statement is used instead.
     * In this case, wildcards ('?') are used in the query string instead
     * of the actual values ('DELETE FROM patients WHERE id=?'). The values
     * are passed to the function as a list of strings.
     * 
     * @param query
     * @param values
     * @return
     * @throws SQLException
     */
    public int prepareAndExecuteUpdate(String query, String[] values)
            throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("query");
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            stmt.setString(i+1, value);
        }
        int status = stmt.executeUpdate(query);
        return status;
    }
    
    /**
     * Perform an insert. The query parameter is an SQL insert command.
     * 
     * returns the auto_generated id of the item that has been inserted, so
     * it can be used in other queries. If nothing is inserted, returns 0.
     *
     * @param query
     * @return
     * @throws SQLException
     */
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
    
    /**
     * Same as executeInsert, except a prepared statement is used instead.
     * In this case, wildcards ('?') are used in the query string instead
     * of the actual values ('INSERT INTO patients ?,?'). The values
     * are passed to the function as a list of strings.
     * 
     * @param query
     * @param values
     * @return
     * @throws SQLException
     */
    public int prepareAndExecuteInsert(String query, String[] values) 
            throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            stmt.setString(i+1, value);
        }
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
    
    /**
     * Perform a select statement. The query parameter is an SQL select command.
     * 
     * returns a ResultSet that the caller can query for more information.
     * 
     * @param query
     * @return
     * @throws SQLException
     */
    public List<Map<String, String>> executeSelect(String query) 
        throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet res = stmt.getResultSet();
        
        ResultSetMetaData resData = res.getMetaData();
        int columnCount = resData.getColumnCount();
        List<String> names = new ArrayList<>();
        
        for (int i = 0; i < columnCount; i++) {
            names.add(resData.getColumnName(i));
        }
        
        List<Map<String, String>> valueMaps = new ArrayList<>();
        while (res.next()) {
            Map<String, String> valueMap = new HashMap<>();
            for (String name : names) {
                valueMap.put(name, res.getString(name));
            }
            valueMaps.add(valueMap);
        }
        return valueMaps;
    }
    
    /**
     * Same as executeSelect, except a prepared statement is used instead.
     * In this case, wildcards ('?') are used in the query string instead
     * of the actual values ('SELECT * FROM patients WHERE id=?'). The values
     * are passed to the function as a list of strings.
     * 
     * @param query
     * @param values
     * @return
     * @throws SQLException
     */
    public ResultSet prepareAndExecuteSelect(String query, String[] values) 
            throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            stmt.setString(i+1, value);
        }
        stmt.execute();
        ResultSet res = stmt.getResultSet();
        return res;
    }
    
}
