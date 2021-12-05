package com.revature.quizzard.user;

import com.revature.quizzard.common.data.CrudDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements CrudDAO<AppUser> {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AppUser findUserByUsername(String username) {
        return null;
    }

    public AppUser findUserByEmail(String email) {
        return null;
    }

    public AppUser findUserByUsernameAndPassword(String username, String password) {
        return sessionFactory.getCurrentSession()
                             .createQuery("from AppUser au where au.username = :username and au.password = :password", AppUser.class)
                             .setParameter("username", username)
                             .setParameter("password", password)
                             .getSingleResult();
    }

    @Override
    public AppUser save(AppUser newUser) {
        sessionFactory.getCurrentSession().save(newUser);
        return newUser;
    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public AppUser findById(String id) {
        return sessionFactory.getCurrentSession().get(AppUser.class, id);
    }

    @Override
    public boolean update(AppUser updatedObj) {
        sessionFactory.getCurrentSession().save(updatedObj);
        return true;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

}
