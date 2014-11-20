/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 * ErrorBean is just a simple wrapper for error messages.
 * 
 * @author Andrew
 */
public class ErrorBean {
    private boolean error;
    private String message;

    /**
     * ErrorBean is a bean, so it has a no args constructor.
     */
    public ErrorBean() {
        error = true;
    }
    
    /**
     * Creates an ErrorBean with the given error status and message.
     * 
     * @param error
     * @param message
     */
    public ErrorBean(boolean error, String message) {
        this.error = true;
        this.message = message;
    }
    
    /**
     * Returns the status.
     * 
     * @return
     */
    public boolean isError() {
        return error;
    }

    /**
     * Set the status.
     * 
     * @param error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * returns the error message.
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the error message.
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
