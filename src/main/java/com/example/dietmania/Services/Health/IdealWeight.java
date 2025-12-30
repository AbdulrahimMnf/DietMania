package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class IdealWeight {

    private IdealWeight(){}


    /**
     * Calculate Ideal Weight using Devine Formula
     * Men: Ideal Weight = 50 + 2.3 × (height(inches) - 60)
     * Women: Ideal Weight = 45.5 + 2.3 × (height(inches) - 60)
     */

    public static double calculate(User user) {
        if (user.getLength() <= 0) {
            return 0;
        }

        double heightInInches = user.getLength() / 2.54;
        double idealWeight;

        if ("MALE".equalsIgnoreCase(user.getGender())) {
            idealWeight = 50 + 2.3 * (heightInInches - 60);
        } else {
            idealWeight = 45.5 + 2.3 * (heightInInches - 60);
        }

        return idealWeight;
    }



}
