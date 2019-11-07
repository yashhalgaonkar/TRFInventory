package com.trf.trfinventory;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name,desc;
    private int total_count, available_count;
    private ArrayList <User> userList;

    //Constructor

    public Product(String name, String desc, int available_count) {
        this.name = name;
        this.desc = desc;
        this.available_count = available_count;
        userList = new ArrayList<>();
    }
    public Product(){}

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", total_count=" + total_count +
                ", available_count=" + available_count +
                ", userList=" + userList +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getAvailable_count() {
        return available_count;
    }

    public void setAvailable_count(int available_count) {
        this.available_count = available_count;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
