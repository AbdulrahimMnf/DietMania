package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class DailyWaterNeeded {

    private DailyWaterNeeded(){}


    /**
     * Calculate Daily Water Needed
     * Formula: 35ml per kg of body weight
     */
    public static double calculate(User user) {
        return user.getKg() * 35.0 / 1000.0; // Convert ml to liters
    }
}
