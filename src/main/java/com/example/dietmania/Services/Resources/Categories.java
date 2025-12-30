package com.example.dietmania.Services.Resources;

import com.example.dietmania.DAO.DbConnection;
import com.example.dietmania.Models.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Categories {

    protected DbConnection DB;

    public Categories() {
        this.DB = new DbConnection();
    }


    public boolean add(Category category) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (" +
                "'" + category.getName() + "'" +
                ")";
        int result = this.DB.execute(sql);
        return result > 0;
    }




    public boolean update(Category category) throws SQLException {

        String sql = "UPDATE categories SET " +
                "name = '" + category.getName() + "' " +
                "WHERE id = '" + category.getId() + "'";

        int result = this.DB.execute(sql);
        return result > 0;
    }




    public boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM categories WHERE id = '" + id + "'";

        int result = this.DB.execute(sql);
        return result > 0;
    }




    public List<Category> list() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY name";
        ResultSet rs = this.DB.select(sql);
        while (rs.next()) {
            Category item = new Category();
            item.setId(rs.getString("id"));
            item.setName(rs.getString("name"));
            categories.add(item);
        }
        return categories;
    }






    public Category getById(String id) throws SQLException {
        String sql = "SELECT * FROM categories WHERE id = '" + id + "'";
        ResultSet rs = this.DB.select(sql);
        if (rs.next()) {
            Category item = new Category();
            item.setId(rs.getString("id"));
            item.setName(rs.getString("name"));
            return item;
        }
        return null;
    }








}
