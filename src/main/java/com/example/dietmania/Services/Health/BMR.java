package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class BMR {

    private BMR(){}

    /**
     * Calculate BMR (Basal Metabolic Rate) using Mifflin-St Jeor Equation
     * Men: BMR = 10 × weight(kg) + 6.25 × height(cm) - 5 × age(years) + 5
     * Women: BMR = 10 × weight(kg) + 6.25 × height(cm) - 5 × age(years) - 161
     */

    public static double calculate(User user) {

        double bmr = 10 * user.getKg() + 6.25 * user.getLength() - 5 * user.getAge();

        if ("MALE".equalsIgnoreCase(user.getGender())) {
            bmr += 5;
        } else {
            bmr -= 161;
        }

        return bmr;
    }


}
