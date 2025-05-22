package org.example;

import java.util.List;

public interface DatabaseDAO<T> {
    void add(T entity);
    T getById(int id);
    T getById(int id1, int id2);
    List<T> getAll();
    void update(T entity);
    void delete(int id);
    void delete(int id1, int id2);
}
