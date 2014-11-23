/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 * contains information about the client making requests to the server. This
 * information is collected in the filter, and stored in the database.
 * 
 * The information gathered is the ip address, the date, the page requested,
 * and the browser used.
 * 
 * Since it's a bean, it can be used in el in the views.
 * 
 * @author Andrew
 */
public class ClientInfo {
    private int id;
    private String ip;
    private String date;
    private String page;
    private String browser;

    /**
     * Bean, so no arg constructor.
     */
    public ClientInfo() {
    }
    
    /**
     * Create a ClientInfo with the following attributes.
     * 
     * @param id
     * @param ip
     * @param date
     * @param page
     * @param browser
     */
    public ClientInfo(int id, String ip, String date, String page, String browser) {
        this.id = id;
        this.ip = ip;
        this.date = date;
        this.page = page;
        this.browser = browser;
    }
    
    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getPage() {
        return page;
    }

    /**
     *
     * @param page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     *
     * @return
     */
    public String getBrowser() {
        return browser;
    }

    /**
     *
     * @param browser
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    
}
