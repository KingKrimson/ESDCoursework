/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Andrew
 */
public class ClientInfo {
    private int id;
    private String ip;
    private String date;
    private String page;
    private String browser;

    public ClientInfo() {
    }
    
    public ClientInfo(int id, String ip, String date, String page, String browser) {
        this.id = id;
        this.ip = ip;
        this.date = date;
        this.page = page;
        this.browser = browser;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    
}
