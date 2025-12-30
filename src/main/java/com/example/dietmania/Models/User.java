package com.example.dietmania.Models;

import java.io.Serializable;

/**
 * User model representing an application user
 * Implements Serializable for session management
 */
public class User implements Serializable {
//    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String email;
    private int kg;
    private int length;
    private String password;
    private String target;
    private int age;
    private String gender;
    private String role; // ADMIN or USER

    public User() {
    }

    public User(String id, String name, String email, int kg, int length, 
                String password, String target, int age, String gender, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.kg = kg;
        this.length = length;
        this.password = password;
        this.target = target;
        this.age = age;
        this.gender = gender;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }
}
