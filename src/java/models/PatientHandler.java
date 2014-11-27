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
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 * A bunch of static methods that deal with manipulating the patients in the
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
public class PatientHandler {

    /**
     * This method, true to it's name, retrieves all of the patients from the
     * database. The patients in the returned ArrayList only contains the
     * patients name and id. If you need the patients Medicine list or
     * consultation fee, you can do that with the methods provided by the
     * patient object.
     *
     * @param dbh
     * @return
     * @throws SQLException
     */
    public static List<Patient> retrieveAllPatients(DatabaseHandler dbh)
            throws SQLException {
        ArrayList<Patient> patients = new ArrayList<>();
        List<Map<String, String>> results = dbh.executeSelect("SELECT * FROM patients");

        for (Map<String, String> result : results) {
            int id = Integer.parseInt(result.get("id"));
            String name = result.get("name");
            Patient p = new Patient(id, name);
            patients.add(p);
        }

        return patients;
    }

    /**
     * Much like the other method, except that it checks the proffered session
     * for a patient list, to reduce the pressure put on the database. If the
     * list doesn't exist in the session, or if the list has been updated, then
     * the database is queried.
     *
     * @param dbh
     * @param session
     * @param updated
     * @return
     * @throws SQLException
     */
    public static List<Patient> retrieveAllPatients(DatabaseHandler dbh, HttpSession session, boolean updated)
            throws SQLException {
        List<Patient> patients = (List<Patient>) session.getAttribute("cache_patients");

        if (patients == null || updated) {
            patients = retrieveAllPatients(dbh);
            session.setAttribute("cache_patients", patients);
        }
        return patients;
    }

    /**
     * Given an id, retrieve the corresponding patient from the database. The
     * returned patient has the consultationFee and medicines members
     * initilised, as chances are if you want an individual patient, you also
     * want to see what they owe the surgery.
     *
     * @param dbh
     * @param id
     * @return
     * @throws SQLException
     */
    public static Patient retrievePatient(DatabaseHandler dbh, Integer id)
            throws SQLException {
        Patient p = new Patient();
        String qId = quotify(id.toString());

        List<Map<String, String>> patientResults = dbh.executeSelect("SELECT * from patients WHERE id=" + qId);

        // This should always fire, since the id passed to it has to be one in
        // the database. Better safe than sorry, though.
        if (!patientResults.isEmpty()) {
            // Since there will only ever be one patient with a given id, grab
            // the first and only result.
            Map<String, String> patientResult = patientResults.get(0);

            // Grab the id and name from the result...
            p.setId(Integer.parseInt(patientResult.get("id")));
            p.setName(patientResult.get("name"));
            
            // Grab the rest of the patients values, using the patient's objects
            // retrival methods.
            p.retrieveMedicines(dbh);
            p.retrieveConsultationFee(dbh);
            p.calculateTotalFee();
        }

        return p;
    }

    /**
     * Adds a patient to the database, along with their medicine list, and their
     * initial consultation fee. medicineIds is probably a set of checkboxes, so
     * multiple medicines can be added.
     *
     * @param dbh
     * @param name
     * @param medicineIds
     * @param consultationFee
     * @throws SQLException
     */
    public static void addPatient(DatabaseHandler dbh, String name, String[] medicineIds, Integer consultationFee)
            throws SQLException {
        String qName = quotify(name);
        Integer patientId = dbh.executeInsert("INSERT INTO patients (name) VALUES (" + qName + ")");
        String qPId = quotify(patientId.toString());

        for (String medicineId : medicineIds) {
            String qMId = quotify(medicineId);
            dbh.executeInsert("INSERT INTO patient_medicines (patient_id, medicine_id) "
                    + "VALUES (" + qPId + "," + qMId + ")");
        }
        String qCost = quotify(consultationFee.toString());
        dbh.executeInsert("INSERT INTO patient_consultations (patient_id, cost) "
                + "VALUES (" + qPId + "," + qCost + ")");
    }

    /**
     * Remove the patient with the given id from the database. Assuming, that
     * is, that they have paid any outstanding bills. If they haven't, they're
     * staying in the database. Returns null if the patient has been removed,
     * and the patient if they still have bills to pay.
     *
     * @param dbh
     * @param id
     * @param name
     * @return
     * @throws SQLException
     */
    public static boolean removePatient(DatabaseHandler dbh, Integer id, String name)
            throws SQLException {
        boolean removed = false;
        Patient p = new Patient(id, name);
        p.retrieveMedicines(dbh);
        p.retrieveConsultationFee(dbh);
        int totalFee = p.calculateTotalFee();

        // if totalFee is less than (somehow?) or equal to zero, then the patients bill is clear,
        // and we can remove them.
        if (totalFee <= 0) {
            String qId = quotify(id.toString());
            dbh.executeUpdate("DELETE FROM patients WHERE id=" + qId);
            removed = true;
        }

        return removed;
    }

    /**
     * Add medicines to the patient with the given id. medicineIds is probably a
     * set of checkboxes, so multiple medicines can be added.
     *
     * @param dbh
     * @param id
     * @param medicineIds
     * @throws SQLException
     */
    public static void addMedicines(DatabaseHandler dbh, Integer id, String[] medicineIds)
            throws SQLException {
        String qPid = quotify(id.toString());
        for (String mId : medicineIds) {
            String qMid = quotify(mId);
            dbh.executeUpdate("INSERT INTO patient_medicines (patient_id, medicine_id) "
                    + "VALUES (" + qPid + ", " + qMid + ")");
        }
    }

    /**
     * Pay the bill of the patient with the given id. In the real system, this
     * would trundle off to paypal or something, but in the prototype it just
     * removes any medicines and fees linked to the patient.
     *
     * @param dbh
     * @param id
     * @throws SQLException
     */
    public static void payBill(DatabaseHandler dbh, Integer id)
            throws SQLException {
        String qId = quotify(id.toString());
        dbh.executeUpdate("DELETE FROM patient_medicines WHERE patient_id=" + qId);
        dbh.executeUpdate("DELETE FROM patient_consultations WHERE patient_id=" + qId);
    }

    private static String quotify(String s) {
        return "'" + s + "'";
    }
}
