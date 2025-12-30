package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class DailyBodyOilNeeded {

    private DailyBodyOilNeeded(){}
    /**
     * Calculate Daily Body Oil (Fat) Needed
     * Formula: 20-35% of total daily calories
     * Using BMR * 1.5 (moderate activity) * 0.275 (27.5% fat) / 9 (calories per gram)
     */
    public static double calculate(User user) {
        double bmr = BMR.calculate(user);
        double dailyCalories = bmr * 1.5; // Moderate activity level
        return (dailyCalories * 0.275) / 9; // 27.5% from fat, 9 cal per gram
    }
}
