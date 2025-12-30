package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class DailyProteinNeeded {

    private DailyProteinNeeded(){}

    /**
     * Calculate Daily Protein Needed
     * Formula: 0.8g per kg (sedentary) to 1.6g per kg (active)
     * Using average of 1.2g per kg
     */
    public static double calculate(User user)
    {
        return user.getKg() * 1.2;
    }

}
