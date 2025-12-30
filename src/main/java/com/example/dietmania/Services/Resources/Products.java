package com.example.dietmania.Services.Resources;

import com.example.dietmania.DAO.DbConnection;
import com.example.dietmania.Models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Products {

    protected DbConnection DB;

    public Products() {
        this.DB = new DbConnection();
    }


    public boolean add(Product product) throws SQLException {
        String sql = "INSERT INTO products (name,description,kcal, protein,carbohydrate,bodyOil,category_id) VALUES (" +
                "'" + product.getName() + "'," +
                "'" + product.getDescription() + "'," +
                "'" + product.getKcal() + "'," +
                "'" + product.getProtein() + "'," +
                "'" + product.getCarbohydrate() + "'," +
                "'" + product.getBodyOil() + "'," +
                "'" + product.getCategory_id() + "'" +
                ")";
        return this.DB.execute(sql) > 0;
    }







    public boolean update(Product product) throws SQLException {
        String sql = "UPDATE products SET " +
                "name= '" + product.getName() + "', " +
                "description = '" + product.getDescription() + "', " +
                "kcal = '" + product.getKcal() + "', " +
                "protein = '" + product.getProtein() + "', " +
                "carbohydrate = '" + product.getCarbohydrate() + "', " +
                "bodyOil ='" + product.getBodyOil() + "', " +
                "category_id = '" + product.getCategory_id() + "' " +
                "WHERE id = '" + product.getId() + "'";

        return this.DB.execute(sql) > 0;
    }


    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM products WHERE id='" + id + "'";
        return this.DB.execute(sql) > 0;
    }


    public List<Product> list() throws SQLException {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products ORDER BY category_id DESC";

        ResultSet rs = this.DB.select(sql);
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getString("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setKcal(rs.getDouble("kcal"));
            product.setProtein(rs.getDouble("protein"));
            product.setCarbohydrate(rs.getDouble("carbohydrate"));
            product.setBodyOil(rs.getDouble("bodyOil"));
            product.setCategory_id(rs.getString("category_id"));
            products.add(product);
        }
        return products;
    }





    public Product getById(String id) throws SQLException {


        String sql = "SELECT * FROM products WHERE id= '" + id + "'";

        ResultSet rs = this.DB.select(sql);
        if (rs.next()) {
            Product product = new Product();
            product.setId(rs.getString("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setKcal(rs.getDouble("kcal"));
            product.setProtein(rs.getDouble("protein"));
            product.setCarbohydrate(rs.getDouble("carbohydrate"));
            product.setBodyOil(rs.getDouble("bodyOil"));
            product.setCategory_id(rs.getString("category_id"));

            return product;
        }

        return null;
    }


    // GET BY Category id for products
    // maybe late we add search by like %% method
    // zamana bagli dasdsad
    public List<Product> getByCategory(String id) throws SQLException {

        List<Product> products = new ArrayList<>();


        String sql = "SELECT * FROM products WHERE category_id = '" + id + "' ORDER BY created_at DESC";

        ResultSet rs = this.DB.select(sql);

        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getString("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setKcal(rs.getDouble("kcal"));
            product.setProtein(rs.getDouble("protein"));
            product.setCarbohydrate(rs.getDouble("carbohydrate"));
            product.setBodyOil(rs.getDouble("bodyOil"));
            product.setCategory_id(rs.getString("category_id"));
            products.add(product);
        }
        return products;
    }
}
