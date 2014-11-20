/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 * A Medicine is a medicine that Dr. Fatal has in stock. It has an id, a name,
 * and a cost.
 * 
 * @author Andrew
 */
public class Medicine {
    private int id;
    private String name;
    private int cost;
    
    /**
     * Medicine is a bean, so it has a no args constructor.
     */
    public Medicine() {}
    
    /**
     * Creates a Medicine object with the given id, name and cost. 
     * 
     * @param id
     * @param name
     * @param cost
     */
    public Medicine(int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
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
     * Get the name.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the cost.
     * 
     * @return
     */
    public int getCost() {
        return cost;
    }

    /**
     * Set the cost.
     * 
     * @param cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    
}
