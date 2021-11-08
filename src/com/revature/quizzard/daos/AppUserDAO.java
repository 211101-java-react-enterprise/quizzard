package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.List;

import java.io.*;
import java.util.UUID;

public class AppUserDAO implements CrudDAO<AppUser> {

    // TODO: Implement me!
    public AppUser findUserByUsernameAndPassword(String username, String password) {
        File usersFile = new File("resources/data.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(usersFile));
            String currentline = reader.readLine();
            while(currentline !=null) {

                String dataValues [] = currentline.split(":");
                currentline = reader.readLine();
                String fileUsername = dataValues[4];
                String filePassword = dataValues[5];
                if(fileUsername.equals(username) && filePassword.equals(password)) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println("[DEBUG] - AppUser#toString: " + newUser);
            System.out.println("[DEBUG] - AppUser#toFileString: " + newUser. toFileString());
            fileWriter.write(newUser.toFileString() + "\n");

        } catch (Exception e) {
            e.printStackTrace(); // leave for debugging purposes (preferably, write it to a file) - definitely remove before "production"
            throw new RuntimeException("Error persisting user to file.");
        }

        return newUser;
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
