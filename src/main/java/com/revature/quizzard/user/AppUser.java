package com.revature.quizzard.user;

import com.revature.quizzard.set.StudySet;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR CHECK (first_name <> '')")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR CHECK (last_name <> '')")
    private String lastName;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR CHECK (email <> '')")
    private String email;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR CHECK (LENGTH(username) >= 4)")
    private String username;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR CHECK (LENGTH(username) >= 4)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", columnDefinition = "VARCHAR DEFAULT 'LOCKED'")
    private AccountType accountType;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<StudySet> userStudySets;

    public AppUser() {
        super();
    }

    public AppUser(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public AppUser(String id, String firstName, String lastName, String email, String username, String password) {
        this(firstName, lastName, email, username, password);
        this.id = id;
    }

    public AppUser(String id, String firstName, String lastName, String email, String username, String password, AccountType accountType) {
        this(id, firstName, lastName, email, username, password);
        this.accountType = accountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<StudySet> getUserStudySets() {
        return userStudySets;
    }

    public void setUserStudySets(List<StudySet> userStudySet) {
        this.userStudySets = userStudySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && Objects.equals(firstName, appUser.firstName) && Objects.equals(lastName, appUser.lastName) && Objects.equals(email, appUser.email) && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password) && accountType == appUser.accountType && Objects.equals(userStudySets, appUser.userStudySets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password, accountType, userStudySets);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountType=" + accountType +
                '}';
    }

    public enum AccountType {
        ADMIN, DEV, BASIC, PREMIUM, LOCKED, BANNED
    }

}