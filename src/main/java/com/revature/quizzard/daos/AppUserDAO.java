package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO Refactor to use Spring ORM

@Repository
public class AppUserDAO implements CrudDAO<AppUser> {

    public AppUser findUserByUsername(String username) {
        return null;
    }

    public AppUser findUserByEmail(String email) {
        return null;
    }

    public AppUser findUserByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public AppUser save(AppUser newUser) {
        return null;
    }

    @Override
    public List<AppUser> findAll() {
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
