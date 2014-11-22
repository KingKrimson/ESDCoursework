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
import javax.servlet.http.HttpSession;

/**
 * A bunch of static methods that deal with manipulating the medicines in the
 * database. This is in a model, and not a servlet, so that if we decide to port
 * the system to a GUI, rather than a website, we can easily swap out the
 * models, and quickly write new controllers. It's always good to separate
 * business logic from control logic.
 *
 * All of these methods can throw SQLExceptions, which the controller can then
 * deal with.
 *
 * @author Andrew
 */
public class MedicineHandler {

    /**
     * Retrieves all of the medicines from the database.
     *
     * @param dbh
     * @return
     * @throws SQLException
     */
    public static List<Medicine> retrieveAllMedicines(DatabaseHandler dbh)
            throws SQLException {
        List<Medicine> medicines = new ArrayList<>();

        ResultSet res = dbh.executeSelect("SELECT * FROM medicine");

        while (res.next()) {
            int id = res.getInt("id");
            String name = res.getString("name");
            int cost = res.getInt("cost");

            Medicine m = new Medicine(id, name, cost);
            medicines.add(m);
        }

        return medicines;
    }

    /**
     * Much like the other method, except that it checks the proffered session
     * for a medicine list, to reduce the pressure put on the database. 
     * If the list doesn't exist in the session, or if the list has been updated,
     * then the database is queried.
     * 
     * @param dbh
     * @param session
     * @param updated
     * @return
     * @throws SQLException
     */
    public static List<Medicine> retrieveAllMedicines(DatabaseHandler dbh, HttpSession session, boolean updated)
            throws SQLException {
        List<Medicine> medicines = (List<Medicine>) session.getAttribute("cache_medicines");

        if (medicines == null || updated) {
            medicines = new ArrayList<>();
            ResultSet res = dbh.executeSelect("SELECT * FROM medicine");

            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int cost = res.getInt("cost");

                Medicine m = new Medicine(id, name, cost);
                medicines.add(m);
            }
            session.setAttribute("cache_medicines", medicines);
        }
        return medicines;
    }

    /**
     * Adds a medicine with the given name and cost to the database
     *
     * @param dbh
     * @param name
     * @param cost
     * @throws SQLException
     */
    public static void addMedicine(DatabaseHandler dbh, String name, Integer cost)
            throws SQLException {
        String qName = quotify(name);
        String qCost = quotify(cost.toString());
        dbh.executeUpdate("INSERT INTO medicine (name, cost) "
                + "VALUES (" + qName + ", " + qCost + ")");
    }

    /**
     * Removes the medicine with the given id from the database. This isn't
     * actually used in the website, but it doesn't hurt to have it.
     *
     * @param dbh
     * @param id
     * @throws SQLException
     */
    public static void removeMedicine(DatabaseHandler dbh, Integer id)
            throws SQLException {
        String qId = quotify(id.toString());
        dbh.executeUpdate("DELETE FROM medicine WHERE id=" + qId);
    }

    /**
     *
     * Changes the price of the medicine with the given id.
     *
     * @param dbh
     * @param id
     * @param newCost
     * @throws SQLException
     */
    public static void changeMedicinePrice(DatabaseHandler dbh, Integer id, Integer newCost)
            throws SQLException {
        String qId = quotify(id.toString());
        String qCost = quotify(newCost.toString());
        dbh.executeUpdate("UPDATE medicine SET cost=" + qCost + " WHERE id=" + qId);
    }

    private static String quotify(String s) {
        return "'" + s + "'";
    }

}
