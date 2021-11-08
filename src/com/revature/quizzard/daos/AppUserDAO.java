package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;

public class AppUserDAO implements CrudDAO<AppUser> {

    @Override
    public AppUser save(AppUser newObj) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public AppUser findById(String id) {
        return null;
    }

    @Override
    public boolean update(AppUser updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }
}
