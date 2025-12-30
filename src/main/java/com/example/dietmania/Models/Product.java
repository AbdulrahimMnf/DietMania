package com.example.dietmania.Models;

/**
 * Product model representing a food product
 */
public class Product {
    private String id;
    private String name;
    private String description;
    private double kcal;
    private double protein;
    private double carbohydrate;
    private double bodyOil;
    private String category_id;

    public Product() {
    }

    public Product(String id, String name, String description, double kcal, 
                   double protein, double carbohydrate, double bodyOil, String category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.kcal = kcal;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.bodyOil = bodyOil;
        this.category_id = category_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getBodyOil() {
        return bodyOil;
    }

    public void setBodyOil(double bodyOil) {
        this.bodyOil = bodyOil;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return name;
    }
}
