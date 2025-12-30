package com.example.dietmania.Services.Resources;

import com.example.dietmania.DAO.DbConnection;
import com.example.dietmania.Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Users {

    protected DbConnection DB;

    public Users() {
        this.DB = new DbConnection();
    }


    public boolean add(User user) throws SQLException {
        // Register method is ready soo if there time i will make a ui for it
        //

        String sql = "INSERT INTO users (name, email, kg, length, password, target, age, gender, role) VALUES (" +
                "'" + user.getName() + "'," +
                "'" + user.getEmail() + "'," +
                "'" + user.getKg() + "'," +
                "'" + user.getLength() + "'," +
                "'" + user.getPassword() + "'," +
                "'" + user.getTarget() + "'," +
                "'" + user.getAge() + "'," +
                "'" + user.getGender() + "'," +
                "'" + user.getRole() + "'" +
                ")";

        return this.DB.execute(sql) > 0;
    }


    public boolean update(User user) throws SQLException {

        String sql = "UPDATE users SET " +
                "name = '" + user.getName() + "', " +
                "email = '" + user.getEmail() + "', " +
                "kg = '" + user.getKg() + "', " +
                "length = '" + user.getLength() + "', " +
                "password = '" + user.getPassword() + "', " +
                "target = '" + user.getTarget() + "', " +
                "age = '" + user.getAge() + "', " +
                "gender = '" + user.getGender() + "', " +
                "role = '" + user.getRole() + "' " +
                "WHERE id = '" + user.getId() + "'";

        return this.DB.execute(sql) > 0;
    }


    public boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM users WHERE id = '" + id + "'";
        return this.DB.execute(sql) > 0;
    }


    public List<User> list() throws SQLException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY name";

        ResultSet rs = this.DB.select(sql);

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setKg(rs.getInt("kg"));
            user.setLength(rs.getInt("length"));
            user.setPassword(rs.getString("password"));
            user.setTarget(rs.getString("target"));
            user.setAge(rs.getInt("age"));
            user.setGender(rs.getString("gender"));
            user.setRole(rs.getString("role"));

            users.add(user);
        }

        return users;
    }


    public User get(String id) throws SQLException {

        String sql = "SELECT * FROM users WHERE id = '" + id + "'";

        ResultSet rs = this.DB.select(sql);

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setKg(rs.getInt("kg"));
            user.setLength(rs.getInt("length"));
            user.setPassword(rs.getString("password"));
            user.setTarget(rs.getString("target"));
            user.setAge(rs.getInt("age"));
            user.setGender(rs.getString("gender"));
            user.setRole(rs.getString("role"));
            return user;
        }
        return null;
    }
}
