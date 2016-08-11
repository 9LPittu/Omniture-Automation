package com.jcrew.pojo;

import com.github.javafaker.Faker;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.UserReader;
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

    private static UserReader reader = UserReader.getUserReader();
    private static User user = null;
    private static User fakeUser = null;
    private static User noDefaultUser = null;
    private static User multipleUser = null;
    private static User noDefaultMultipleUser = null;

    public static final String DEFAULT = "user";
    public static final String NO_DEFAULT = "noDefaultUser";
    public static final String MULTIPLE = "multiple";
    public static final String NO_DEFAULT_MULTIPLE = "noDefaultMultiple";
    
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    public User(String userName, String password, String firstName, String lastName, String country) {
        this.email = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;

    }

    public static User getUser() {
        UsersHub usersHub = UsersHub.getInstance();
        try {
            return usersHub.getUser();
        } catch (SQLException e) {
            logger.error("Failed to get user from DB. getting from properties file");
            return new User(true);
        }

    }
    
    public static User getUser(String userType) {
        User theUser;

        switch (userType) {
            default:
            case "default":
                if (user == null) {
                    String userId = reader.getProperty(DEFAULT);
                    user = new User(userId);
                }
                theUser = user;
                break;
            case "noDefaultUser":
                if (noDefaultUser == null) {
                    String userId = reader.getProperty(NO_DEFAULT);
                    noDefaultUser = new User(userId);
                }
                theUser = noDefaultUser;
                break;
            case "multiple":
                if (multipleUser == null) {
                    String userId = reader.getProperty(MULTIPLE);
                    multipleUser = new User(userId);
                }
                theUser = multipleUser;
                break;
            case "noDefaultMultiple":
                if (noDefaultMultipleUser == null) {
                    String userId = reader.getProperty(NO_DEFAULT_MULTIPLE);
                    noDefaultMultipleUser = new User(userId);
                }
                theUser = noDefaultMultipleUser;
                break;
        }

        return theUser;
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
    
    private User(String userId) {
        this.email = reader.getProperty(userId + ".email");
        this.password = reader.getProperty(userId + ".password");
        this.firstName = reader.getProperty(userId + ".firstName", "Automation");
        this.lastName = reader.getProperty(userId + ".lastName", "User");
        this.country = reader.getProperty(userId + ".country", "United States");
        this.countryCode = reader.getProperty(userId + ".countryCode", "US");

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

    public static String getSomePassword(int characters) {
        Faker faker = new Faker();
        return faker.lorem().fixedString(characters);
    }
}
