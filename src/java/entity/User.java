/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author user
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fname;
    private String lname;
    private String pnumber;
    private String login;
    private String password;
    private String salts;
    private boolean active;


    public User() {
    }

    public User(String fname, String lname, String pnumber, String login, String password, String salts, boolean active) {
        this.fname = fname;
        this.lname = lname;
        this.pnumber = pnumber;
        this.login = login;
        this.password = password;
        this.salts = salts;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalts() {
        return salts;
    }

    public void setSalts(String salts) {
        this.salts = salts;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fname=" + fname + ", lname=" + lname + ", pnumber=" + pnumber + ", login=" + login + ", password=" + password + ", salts=" + salts + ", active=" + active + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.fname);
        hash = 31 * hash + Objects.hashCode(this.lname);
        hash = 31 * hash + Objects.hashCode(this.pnumber);
        hash = 31 * hash + Objects.hashCode(this.login);
        hash = 31 * hash + Objects.hashCode(this.password);
        hash = 31 * hash + Objects.hashCode(this.salts);
        hash = 31 * hash + (this.active ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.active != other.active) {
            return false;
        }
        if (!Objects.equals(this.fname, other.fname)) {
            return false;
        }
        if (!Objects.equals(this.lname, other.lname)) {
            return false;
        }
        if (!Objects.equals(this.pnumber, other.pnumber)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.salts, other.salts)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
