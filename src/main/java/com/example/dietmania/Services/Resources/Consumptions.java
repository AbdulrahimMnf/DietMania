package com.example.dietmania.Services.Resources;

import com.example.dietmania.DAO.DbConnection;
import com.example.dietmania.Models.Consumption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Consumptions {

    protected DbConnection DB;

    public Consumptions() {
        this.DB = new DbConnection();
    }



    // en son buna bakariz AI kullanilidi burda
    // isi ve tekrar azaltmak icin
    // acaba trait kavrami var mi arastir sonra !!!!!!!!!!

    public boolean add(Consumption consumption) throws SQLException {

        String productsStr = consumption.getProducts().toString();

        String sql = "INSERT INTO consumptions (totalKcal, user_id, products) VALUES (" +
                "'" + consumption.getTotalKcal() + "'," +
                "'" + consumption.getUser_id() + "'," +
                "'" + productsStr + "'" +
                ")";

        int result = this.DB.execute(sql);
        return result > 0;
    }


    public boolean update(Consumption consumption) throws SQLException {

        String productsStr = consumption.getProducts().toString();

        String sql = "UPDATE consumptions SET " +
                "totalKcal = '" + consumption.getTotalKcal() + "', " +
                "products = '" + productsStr + "' " +
                "WHERE id = '" + consumption.getId() + "'";

        int result = this.DB.execute(sql);
        return result > 0;
    }


    public boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM consumptions WHERE id = '" + id + "'";
        return this.DB.execute(sql) > 0;
    }


    public List<Consumption> listByUser(String userId) throws SQLException {

        List<Consumption> consumptions = new ArrayList<>();

        String sql = "SELECT * FROM consumptions WHERE user_id = '" + userId + "' ORDER BY created_at DESC";

        ResultSet rs = this.DB.select(sql);

        while (rs.next()) {
            Consumption consumption = new Consumption();
            consumption.setId(rs.getString("id"));
            consumption.setCreated_at(rs.getString("created_at"));
            consumption.setTotalKcal(rs.getDouble("totalKcal"));
            consumption.setUser_id(rs.getString("user_id"));
            consumption.setProducts(parseProductsString(rs.getString("products")));

            consumptions.add(consumption);
        }

        return consumptions;
    }


    public Consumption get(String id) throws SQLException {

        String sql = "SELECT * FROM consumptions WHERE id = '" + id + "'";

        ResultSet rs = this.DB.select(sql);

        if (rs.next()) {
            Consumption consumption = new Consumption();
            consumption.setId(rs.getString("id"));
            consumption.setCreated_at(rs.getString("created_at"));
            consumption.setTotalKcal(rs.getDouble("totalKcal"));
            consumption.setUser_id(rs.getString("user_id"));
            consumption.setProducts(parseProductsString(rs.getString("products")));
            return consumption;
        }

        return null;
    }

    private List<String> parseProductsString(String productsStr) {

        List<String> products = new ArrayList<>();

        if (productsStr != null && !productsStr.isEmpty()) {
            productsStr = productsStr.replace("[", "").replace("]", "");
            String[] items = productsStr.split(",");

            for (String item : items) {
                String clean = item.trim().replace("'", "").replace("\"", "");
                if (!clean.isEmpty()) {
                    products.add(clean);
                }
            }
        }

        return products;
    }
}
