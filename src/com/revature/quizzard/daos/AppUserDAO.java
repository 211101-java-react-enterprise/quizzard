package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.List;

import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

public class AppUserDAO implements CrudDao<AppUser> {

    @Override
    public AppUser save(AppUser newUser) {
        // try-with-resources (allows for instantiation of autocloseable resources)
        // will close automatically if exception is thrown. (can have multiple items separated by ";")

        try (FileWriter fileWriter =  new FileWriter(new File("resources/data.txt"), true);){
            String uuid = UUID.randomUUID().toString();
            newUser.setId(uuid);


            // the true argument here allows for us to append to the file, rather than completely overwriting it
            fileWriter.write(newUser.toFileString() + "\n");
            fileWriter.close();

        } catch (Exception e) {
            // leave for debugging purposes, preferably write to file, definitely remove for production
            e.printStackTrace();
            throw new RuntimeException("Error persisting user to file");

        } finally {

        }
        return new AppUser("","","","","");
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
