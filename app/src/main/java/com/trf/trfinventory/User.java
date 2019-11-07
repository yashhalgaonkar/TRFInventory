package com.trf.trfinventory;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String fname,lname,email,domain,gr;
    private ArrayList<Product> takeOutList;

    public User(String gr, String fname, String lname, String email, String domain) {
        this.gr = gr;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.domain = domain;
        takeOutList = new ArrayList<>();
    }

    public String getGr() {
        return gr;
    }

    public void setGr(String gr) {
        this.gr = gr;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public ArrayList<Product> getTakeOutList() {
        return takeOutList;
    }

    public void setTakeOutList(ArrayList<Product> takeOutList) {
        this.takeOutList = takeOutList;
    }
    public int getTotalTakeOut()
    {
        return takeOutList.size();
    }
}

