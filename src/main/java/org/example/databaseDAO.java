package org.example;
import java.sql.SQLException;
import java.util.Scanner;

import java.util.List;

public interface databaseDAO<T> {
    void add(T t);
    T getById(int id) throws SQLException;
    List<T> getAll();
    void update(T t);
    void delete(int id);
}
