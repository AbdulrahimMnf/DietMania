package com.example.dietmania.DAO;

import java.sql.SQLException;
import java.util.List;


public interface ICrud<T> {
    boolean add(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(String id) throws SQLException;
    List<T> list() throws SQLException;
    T get(String id) throws SQLException;
}


