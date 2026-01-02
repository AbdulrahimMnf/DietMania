package com.example.dietmania.Services.Health;

import com.example.dietmania.Models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class DailyKcalNeeded {

    private DailyKcalNeeded() {}

    /**
     * Calculates daily calories needed based on BMR and activity level
     */
    public static double calculate(User user) {


        double x = 0;
        switch (user.getTarget())
        {
            case "LOSE WIGHT":
                x -= 300;
                break;
            case "EARN WIGHT":
                x += 300;
                break;
            default:
                break;
        }



        double bmr;

        if ("male".equalsIgnoreCase(user.getGender())) {
            bmr = 10 * user.getKg() + 6.25 * user.getLength() - 5 * user.getAge() + 5;
        } else {
            bmr = 10 * user.getKg() + 6.25 * user.getLength() - 5 * user.getAge() - 161;
        }
        return bmr * 1.2 + (x);
    }



}
