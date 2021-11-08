package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.LinkedList;
import com.revature.quizzard.util.List;

import java.io.*;
import java.util.UUID;

public class AppUserDAO implements CrudDAO<AppUser> {

    // TODO: Implement me!
    public AppUser findUserByUsernameAndPassword(String username, String password) {
        // Read file to variable
        // Loop through lines of file as LinkedList
        //// Split the file by colons, save as another LinkedList
        // We'll know where in each nested list the username/password should be
        // Return the whole user record (line of text file)
        File file = new File("resources/data.txt");
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file));) {
            String line = fileReader.readLine();
            System.out.println(line);
            while(line != null){
                String[] lineBits = line.split(":");
                String lineUsername = lineBits[4];
                String linePassword = lineBits[5];

                if (username.equals(lineUsername) && password.equals(linePassword)) {
                    AppUser foundUser = new AppUser(lineBits[1], lineBits[2], lineBits[3], lineBits[4], lineBits[5]);
                    foundUser.setId(lineBits[0]);
                    return foundUser;
                } else {
                    line = fileReader.readLine();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

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
