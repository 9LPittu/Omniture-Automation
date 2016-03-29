package com.jcrew.steps;

import com.jcrew.page.LogIn;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class LogInSteps extends DriverFactory{
    LogIn logIn = new LogIn(getDriver());

    @When("User fills user data")
    public void fills_user_data(){
        logIn.signIn();
    }
}
