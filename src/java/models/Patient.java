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
 *
 * @author Andrew
 */
public class Patient {

    private int id = -1;
    private int consultationFee = 0;
    private int totalFee;
    private String name;
    private List<Medicine> medicines;

    public Patient() {
        medicines = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalOwed) {
        this.totalFee = totalOwed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public List<Medicine> retrieveMedicines(DatabaseHandler dbh) 
            throws SQLException {
        String query = "SELECT medicine.id, medicine.name, medicine.cost "
                + "FROM medicine JOIN patient_medicines ON medicine.id = patient_medicines.medicine_id "
                + "JOIN patients ON patient_medicines.patient_id = patient.id "
                + "WHERE patient.id = " + id;
        ResultSet medicineRes = dbh.executeSelect(query);

        medicines.clear();
        while (medicineRes.next()) {
            int mId = medicineRes.getInt("id");
            int cost = medicineRes.getInt("cost");
            String name = medicineRes.getString("name");

            Medicine m = new Medicine();
            m.setId(mId);
            m.setName(name);
            m.setCost(cost);

            medicines.add(m);
        }
        return medicines;
    }

    public int retrieveConsultationFee(DatabaseHandler dbh)
            throws SQLException {
        String query = "SELECT patient_consultations.cost "
                    + "FROM patient_consultations"
                    + "JOIN patients ON patient_consultations.id = patients.id "
                    + "WHERE patient.id = " + id;
        ResultSet consultationRes = dbh.executeSelect(query);
        
        while (consultationRes.next()) {
            int fee = consultationRes.getInt("cost");
            this.consultationFee = fee;
        }
        
        return consultationFee;
    }

    public int calculateTotalFee() {
        int totalFee = 0;
        for (Medicine m : medicines) {
            totalFee += m.getCost();
        }
        totalFee += consultationFee;
        this.totalFee = totalFee;
        return totalFee;
    }

}
