package com.example.dietmania.DAO;

import java.util.List;


public interface ICrud<T> {

    boolean add(T entity);
    boolean update(T entity);
    boolean delete(String id);
    List<T> list();
    T get(String id);
}


