/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.klassenbuch;

/**
 *
 * @author nicis
 */
public class Entry {
    private int idEntry;
    private String name;
    private String username;
    private String date;
    private String message;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setIdEntry(int idEntry) {
        this.idEntry = idEntry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdEntry() {
        return idEntry;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
