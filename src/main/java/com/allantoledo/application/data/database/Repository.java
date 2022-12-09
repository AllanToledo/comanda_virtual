package com.allantoledo.application.data.database;

import java.util.List;

public interface Repository<T> {
    void save(T object);

    void update(T object);

    void delete(T object);

    T get(int id);

    List<T> getAll();

}
