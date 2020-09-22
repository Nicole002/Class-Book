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
public class User {

    private final int id;
    private  String userFirstname;
    private  String userLastname;
    private final String username;

    public int getId() {
        return id;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public String getUsername() {
        return username;
    }

    public User(String username, int id) {
        this.id = id;
        this.username = username;
    }


}
