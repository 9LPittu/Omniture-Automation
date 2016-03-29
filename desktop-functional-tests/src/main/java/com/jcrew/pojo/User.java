package com.jcrew.pojo;

import com.jcrew.utils.PropertyReader;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private static User user = null;

    public static User getUser(){
        if(user == null)
            user = new User();

        return user;
    }

    private User() {
        PropertyReader reader = PropertyReader.getPropertyReader();
        String userId = reader.getProperty("userID");
        this.email = reader.getProperty(userId+".email");
        this.password = reader.getProperty(userId+".password");
        this.firstName = reader.getProperty(userId+".firstName");
        this.lastName = reader.getProperty(userId+".lastName");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
