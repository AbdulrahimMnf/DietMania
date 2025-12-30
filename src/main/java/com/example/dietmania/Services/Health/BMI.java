package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

public class BMI {

    private BMI() {}


     // Calculate BMI (Body Mass Index)
    //  Formula: weight (kg) / (height (m))^2
    public static double calculate(User user) {

        double heightInMeters = user.getLength() / 100.0;
        return user.getKg() / (heightInMeters * heightInMeters);
    }


}
