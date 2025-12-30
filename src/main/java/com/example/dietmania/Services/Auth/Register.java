package com.example.dietmania.Services.Auth;


import com.example.dietmania.DAO.DbConnection;
import com.example.dietmania.Models.User;

import java.sql.SQLException;


public class Register {
    

    public boolean register(User user) throws SQLException {

        // make a check if user exist firstly
        // IMPORTENTTTTTTTTTTTTTTTTTTTTTTTTTTTTT


        String sql = "INSERT INTO users (name, email, kg, length, password, target, age, gender) " +
                     "VALUES (" +
                "'" +  user.getName() + "'," +
                "'" + user.getEmail() + "'," +
                "'" + user.getKg() + "'," +
                "'" + user.getLength() + "'," +
                "'" + user.getPassword() + "'," +
                "'" + user.getTarget() + "'," +
                "'" + user.getAge() + "'," +
                "'" + user.getGender() + "')" ;
        DbConnection db = new DbConnection();
        int result =  db.execute(sql);
        // Save User in session file
        //
        //
        //
        return result > 0;
    }


}

