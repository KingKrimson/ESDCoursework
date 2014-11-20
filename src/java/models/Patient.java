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

/**
 * A patient is a patient on Dr. Fatal's customer list. They're stored in the
 * database. A patient has an id, a name, a list of medicines, and a
 * consultation fee.
 *
 * @author Andrew
 */
public class Patient {

    private int id = -1;
    private int consultationFee = 0;
    private int totalFee;
    private String name;
    private List<Medicine> medicines;

    /**
     * No args constructor, so this is a bean.
     */
    public Patient() {
        medicines = new ArrayList<>();
    }

    /**
     * Make patient with the given id.
     * 
     * @param id
     */
    public Patient(int id) {
        this.id = id;
        medicines = new ArrayList<>();
    }

    /**
     * Make patient with the given id and name.
     * 
     * @param id
     * @param name
     */
    public Patient(int id, String name) {
        this.id = id;
        this.name = name;
        medicines = new ArrayList<>();
    }

    /**
     * Make patient with the given id, name, and consultationFee.
     * 
     * @param id
     * @param name
     * @param consultationFee
     */
    public Patient(int id, String name, int consultationFee) {
        this.id = id;
        this.name = name;
        this.consultationFee = consultationFee;
        medicines = new ArrayList<>();
    }

    /**
     * Get the id.
     * 
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the consultation fee.
     * 
     * @return
     */
    public int getConsultationFee() {
        return consultationFee;
    }

    /**
     * Set the consultation fee.
     * 
     * @param consultationFee
     */
    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    /**
     * Get the total fee.
     * 
     * @return
     */
    public int getTotalFee() {
        return totalFee;
    }

    /**
     * Set the total fee.
     * 
     * @param totalFee
     */
    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * Get the name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the medicine list.
     * @return
     */
    public List<Medicine> getMedicines() {
        return medicines;
    }

    /**
     * Set the medicine list.
     * 
     * @param medicines
     */
    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    /**
     * Grab all of the patient's medicines from the database. Patients are linked
     * to their medicines by their id. This returns the medicineList for convenience, 
     * so the programmer doesn't have to call getMedicines afterwards.
     * 
     * @param dbh
     * @return
     * @throws SQLException
     */
    public List<Medicine> retrieveMedicines(DatabaseHandler dbh)
            throws SQLException {
        String qId = quotify(String.valueOf(id));
        String query = "SELECT medicine.id, medicine.name, medicine.cost "
                + "FROM medicine JOIN patient_medicines ON medicine.id = patient_medicines.medicine_id "
                + "JOIN patients ON patient_medicines.patient_id = patients.id "
                + "WHERE patients.id=" + qId;
        ResultSet medicineRes = dbh.executeSelect(query);

        medicines.clear();
        while (medicineRes.next()) {
            int mId = medicineRes.getInt("id");
            int cost = medicineRes.getInt("cost");
            String name = medicineRes.getString("name");

            Medicine m = new Medicine(mId, name, cost);
            medicines.add(m);
        }
        return medicines;
    }

    /**
     * Retrieve the consultation fee from the database. A patients consultation
     * fee is linked to them by their id.
     * 
     * @param dbh
     * @return
     * @throws SQLException
     */
    public int retrieveConsultationFee(DatabaseHandler dbh)
            throws SQLException {
        String qId = quotify(String.valueOf(id));
        String query = "SELECT patient_consultations.cost "
                + "FROM patient_consultations"
                + "JOIN patients ON patient_consultations.id = patients.id "
                + "WHERE patients.id=" + qId;
        ResultSet consultationRes = dbh.executeSelect(query);

        // This is a while, but the patient will only have one consultation fee. 
        // This will only execute once. 
        while (consultationRes.next()) {
            int fee = consultationRes.getInt("cost");
            this.consultationFee = fee;
        }

        return consultationFee;
    }

    /**
     * Calculates the total fee the patient owes, based on the medicine costs,
     * and the consultation fee.
     * 
     * @return
     */
    public int calculateTotalFee() {
        int totalFee = 0;
        for (Medicine m : medicines) {
            totalFee += m.getCost();
        }
        totalFee += consultationFee;
        this.totalFee = totalFee;
        return totalFee;
    }
    
    private String quotify(String s) {
        return "'" + s + "'";
    }

}
