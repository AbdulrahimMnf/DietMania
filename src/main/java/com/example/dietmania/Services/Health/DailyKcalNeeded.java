package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class DailyKcalNeeded {

    private DailyKcalNeeded() {}

    /**
     * Calculates daily calories needed based on BMR and activity level
     */
    public static double calculate(User user) {

        double bmr;

        if ("male".equalsIgnoreCase(user.getGender())) {
            bmr = 10 * user.getKg() + 6.25 * user.getLength() - 5 * user.getAge() + 5;
        } else {
            bmr = 10 * user.getKg() + 6.25 * user.getLength() - 5 * user.getAge() - 161;
        }


        return bmr * 1.2;
    }

}
