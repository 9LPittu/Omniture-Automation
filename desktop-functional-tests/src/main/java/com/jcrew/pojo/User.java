package com.jcrew.pojo;

import com.github.javafaker.Faker;
import com.jcrew.utils.PropertyReader;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private static User user = null;
    private static User fakeUser = null;

    public static User getUser() {
        if (user == null)
            user = new User(true);

        return user;
    }

    public static User getNewFakeUser() {
        Faker faker = new Faker();
        fakeUser = new User(false);
        fakeUser.email = faker.internet().emailAddress().replace("@", "@test.");
        fakeUser.password = faker.lorem().fixedString(6);
        fakeUser.firstName = faker.name().firstName();
        fakeUser.lastName = faker.name().lastName();

        return fakeUser;
    }

    public static User getFakeUser(){
        if (fakeUser == null)
            getNewFakeUser();

        return fakeUser;
    }

    private User(boolean fromProperties) {
        if(fromProperties) {
            PropertyReader reader = PropertyReader.getPropertyReader();
            String userId = reader.getProperty("userID");
            this.email = reader.getProperty(userId + ".email");
            this.password = reader.getProperty(userId + ".password");
            this.firstName = reader.getProperty(userId + ".firstName");
            this.lastName = reader.getProperty(userId + ".lastName");
            this.country = reader.getProperty(userId + ".country", "United States");
        }
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static String getSomePassword(int characters) {
        Faker faker = new Faker();
        return faker.lorem().fixedString(characters);
    }
}
