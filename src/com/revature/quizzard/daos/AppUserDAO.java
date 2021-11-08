package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static java.lang.System.*;

public class AppUserDAO implements CrudDAO<AppUser> {

    // TODO: Implement me!
    public AppUser findUserByUsernameAndPassword(String username, String password) {
        //this.username = username;
        //this.password = password;
        //BufferedReader reader = new BufferedReader(System.in);
        try(BufferedReader reader = new BufferedReader(System.in)){

        }

    }

    @Override
    public AppUser save(AppUser newUser) {

        File usersFile = new File("resources/data.txt");

        // try-with-resources (allows for the instantiation of AutoCloseable types that are implicitly
        // closed when the try logic is finished)
        try (FileWriter fileWriter = new FileWriter(usersFile, true)) {

            // Universally Unique IDentifier (UUID)
            String uuid = UUID.randomUUID().toString();
            newUser.setId(uuid);
            out.println("[DEBUG] - AppUser#toString: " + newUser);
            out.println("[DEBUG] - AppUser#toFileString: " + newUser. toFileString());
            fileWriter.write(newUser.toFileString() + "\n");

        } catch (Exception e) {
            e.printStackTrace(); // leave for debugging purposes (preferably, write it to a file) - definitely remove before "production"
            throw new RuntimeException("Error persisting user to file.");
        }

        return newUser;
    }

    @Override
    public List<AppUser> findAll() {
        return AppUser;
    }

    @Override
    public AppUser findById(String id) {
        return AppUser.id;
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
