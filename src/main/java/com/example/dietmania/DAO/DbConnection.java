package com.example.dietmania.DAO;

import java.sql.*;


public class DbConnection {

    public static final String DBName = "helloworld";
    private static final String DBUser = "root";
    private static final String DBPassword = "";
    private static final String DBUrl = "jdbc:mysql://localhost:3306/" + DBName;

    private  Connection connection = null;


    public DbConnection()
    {
        this.connection = connect();
    }


    // Connect to DB
    public Connection connect() {
        try {
            if (this.connection == null) {
                this.connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            }
        }
        catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
        return connection;
    }




    // CLose Connecting
    public void close() {
        try {
            if (this.connection != null ) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }




    // Used for GET quires
    public  ResultSet select(String query) throws SQLException {
        Statement stmt = this.connection.createStatement();
        return stmt.executeQuery(query);
    }

    //   Example How to use
    //    DbConnection db = new DbConnection();
    //
    //      try {
    //        ResultSet rs = db.get(
    //                "SELECT * FROM categories"
    //        );
    //
    //        while (rs.next()) {
    //            int id = rs.getInt("id");
    //            String name = rs.getString("name");
    //
    //            System.out.println(id + " - " + name);
    //        }
    //
    //    } catch (SQLException e) {
    //        System.out.println(e.getMessage());
    //    } finally {
    //        db.close();
    //    }



   //    Used for Create Update and Delete (POST : INSERT , UPDATE , DELETE) quires
    public int execute(String query) throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }


    //   Example How to use
    //    DbConnection db = new DbConnection();
    //
    //    String name = "Electronics";
    //
    //      try {
    //        int rows = db.execute(
    //                "INSERT INTO categories (name) VALUES ('" + name + "')"
    //        );
    //
    //        if (rows > 0) {
    //            System.out.println("Category added successfully");
    //        }
    //
    //    } catch (SQLException e) {
    //        System.out.println(e.getMessage());
    //    } finally {
    //        db.close();
    //    }


}
