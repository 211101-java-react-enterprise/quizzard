package com.revature.quizzard.daos;

public interface CrudDAO<T> {

    T save(T newObj);
    List<T> findAll();
    T findById(String id);
    boolean update(T updatedObj);
    boolean removeById(String id);

}
