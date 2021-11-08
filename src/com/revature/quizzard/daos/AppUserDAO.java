package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.List;

import java.io.*;
import java.nio.Buffer;
import java.util.UUID;

public class AppUserDAO implements CrudDAO<AppUser> {

    // TODO: Implement me!
    public AppUser findUserByUsernameAndPassword(String username, String password) throws IOException {

        //Reference to an empty app user
        //AppUser userCredentials;
        File userData = new File("resources/data.txt");
        String rawData;
        //make a filter for the strings in the file
        try (BufferedReader reader = new BufferedReader(new FileReader(userData)))
        {
            while ((rawData = reader.readLine()) != null)
            {
                // UUID Name LastName Email Username Password
                String[] currentSelection = rawData.split(":");
                //compare data collected with data from user
                if (username.equals(currentSelection[4]) && password.equals(currentSelection[5]))
                {
                        AppUser userCredentials = new AppUser(currentSelection[1], currentSelection[2], currentSelection[3], currentSelection[4], currentSelection[5], currentSelection[6]);
                        return userCredentials;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //If we get here, we did not find user


      return null;
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
