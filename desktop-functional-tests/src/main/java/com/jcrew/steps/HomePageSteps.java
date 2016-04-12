package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/30/16.
 */
public class HomePageSteps extends DriverFactory{
    HomePage homePage = new HomePage(getDriver());

    @Then("Verify user is in homepage")
    public void verify_user_is_in_hompage(){
        assertTrue("user is in homepage",homePage.isHomePage());
    }

    @When("User closes email capture")
    public void user_closes_email_capture() {
        homePage.closeEmailCapture();
    }

}
