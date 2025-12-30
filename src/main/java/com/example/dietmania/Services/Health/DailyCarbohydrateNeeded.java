package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class DailyCarbohydrateNeeded {
    private DailyCarbohydrateNeeded(){}

    /**
     * Calculate Daily Carbohydrate Needed
     * Formula: 45-65% of total daily calories
     * Using BMR * 1.5 (moderate activity) * 0.5 (50% carbs) / 4 (calories per gram)
     */
    public static double calculate(User user) {
        double bmr = BMR.calculate(user);
        double dailyCalories = bmr * 1.5; // Moderate activity level
        return (dailyCalories * 0.5) / 4; // 50% from carbs, 4 cal per gram
    }
}
