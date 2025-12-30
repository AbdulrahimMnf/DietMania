package com.example.dietmania.Models;

import java.util.List;

/**
 * Consumption model representing a user's food consumption record
 */
public class Consumption {
    private String id;
    private String created_at;
    private double totalKcal;
    private String user_id;
    private List<String> products;

    public Consumption() {
    }

    public Consumption(String id, String created_at, double totalKcal, String user_id, List<String> products) {
        this.id = id;
        this.created_at = created_at;
        this.totalKcal = totalKcal;
        this.user_id = user_id;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public double getTotalKcal() {
        return totalKcal;
    }

    public void setTotalKcal(double totalKcal) {
        this.totalKcal = totalKcal;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
