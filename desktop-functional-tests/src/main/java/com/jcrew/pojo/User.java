package com.jcrew.pojo;

import com.github.javafaker.Faker;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.UsersHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String countryCode;
    private String userCategory;

    private static User fakeUser = null;
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    public static final String CAT_LOYALTY = "loyalty";
    public static final String CAT_NO_LOYALTY = "noLoyalty";

    public User(String userName, String password, String firstName, String lastName, String countryCode,String userCategory) {
        this.email = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryCode = countryCode;
        this.userCategory = userCategory;
    }

    public static User getUser(){
        return getUser("");
    }
    public static User getUser(String userCategory) {
        try {
            return UsersHub.getInstance().getUser(userCategory);
        } catch (SQLException e) {
            logger.error("Failed to get user from DB. getting from properties file");
            return new User(true);
        }
    }

    public static User getNewFakeUser() {
        Faker faker = new Faker();
        fakeUser = new User(false);
        fakeUser.email = faker.internet().emailAddress().replace("@", "@test.").replace("'", "");
        fakeUser.password = faker.lorem().fixedString(6).replace(" ", "?");
        fakeUser.firstName = faker.name().firstName();
        fakeUser.lastName = faker.name().lastName();

        return fakeUser;
    }

    public static User getFakeUser() {
        if (fakeUser == null)
            getNewFakeUser();

        return fakeUser;
    }

    private User(boolean fromProperties) {
        if (fromProperties) {
            PropertyReader reader = PropertyReader.getPropertyReader();
            String userId = reader.getProperty("userID");
            this.email = reader.getProperty(userId + ".email");
            this.password = reader.getProperty(userId + ".password");
            this.firstName = reader.getProperty(userId + ".firstName");
            this.lastName = reader.getProperty(userId + ".lastName");
            this.country = reader.getProperty(userId + ".country", "United States");
            this.countryCode = reader.getProperty(userId + ".countryCode", "US");
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUserCategory(){
        return userCategory;
    }
    public static String getSomePassword(int characters) {
        Faker faker = new Faker();
        return faker.lorem().fixedString(characters);
    }
}
