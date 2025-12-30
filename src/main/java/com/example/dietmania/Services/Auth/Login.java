package com.example.dietmania.Services.Auth;


import com.example.dietmania.DAO.DbConnection;
import com.example.dietmania.Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Login {

    public User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users  WHERE email = '" + email + "' AND password = '" + password + "' ";
        DbConnection db = new DbConnection();
        ResultSet rs = db.select(sql);
        if (rs.next()) {
            User user = new User();
            user.setId( rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email") );
            user.setKg(rs.getInt("kg"));
            user.setLength(rs.getInt("length"));
            user.setAge(rs.getInt("age"));
            user.setTarget(rs.getString("target"));
            user.setGender(rs.getString("gender"));
            user.setRole(rs.getString("role"));
            return user;
        }
        return null;
    }

}

