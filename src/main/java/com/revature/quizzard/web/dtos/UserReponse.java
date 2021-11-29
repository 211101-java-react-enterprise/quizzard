package com.revature.quizzard.web.dtos;

import com.revature.quizzard.models.AppUser;

public class UserReponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String username;

    public UserReponse() {
        super();
    }

    public UserReponse(AppUser creator) {
        this.userId = creator.getId();
        this.firstName = creator.getFirstName();
        this.lastName = creator.getLastName();
        this.username = creator.getUsername();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserReponse{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
