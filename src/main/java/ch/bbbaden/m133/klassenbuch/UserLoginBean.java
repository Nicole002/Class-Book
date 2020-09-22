/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.klassenbuch;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Pattern;

/**
 *
 * @author nicis
 */
@Named(value = "userLoginBean")
@SessionScoped
public class UserLoginBean implements Serializable {

    @Pattern(regexp = "^[a-zA-Z0-9,.!?:;]*$", message = "Unzulässige Zeichen wurden eingegeben")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9,.!?:;]*$", message = "Unzulässige Zeichen wurden eingegeben")
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9,.!?:;]*$", message = "Unzulässige Zeichen wurden eingegeben")
    private String firstname;
    @Pattern(regexp = "^[a-zA-Z0-9,.!?:;]*$", message = "Unzulässige Zeichen wurden eingegeben")
    private String lastname;

    private String entry;
    private User user;
    private List<Entry> entries;

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<Entry> getEntries() {
        LoginDAO dao = new LoginDAO();
        this.entries = dao.getData();
        return this.entries;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEntry() {
        return entry;
    }

    /**
     * Creates a new instance of userLoginBean
     */
    public UserLoginBean() {
    }

    public String doLogin() {
        LoginDAO dao = new LoginDAO();
        this.user = dao.check(username, password);

        if (this.user != null) {
            System.out.println("giiiiiirl");
            return "book.xhtml";
        }

        return "login.xhtml";
    }

    public void doEntry() {
        new LoginDAO().setEintrag(user.getUserFirstname(), user.getUsername(), this.getEntry());
        this.setEntry("");
    }

    public String register() {
        try {
            LoginDAO dao = new LoginDAO();
            dao.setUser(firstname, lastname, username, password);
            return "login.xhtml";
        }catch(Exception e){
            return "register.xhtml";
        }

    }

}
