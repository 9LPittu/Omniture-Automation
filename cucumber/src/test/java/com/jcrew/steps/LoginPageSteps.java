package com.jcrew.steps;

import com.jcrew.page.LoginPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class LoginPageSteps extends DriverFactory {

    private LoginPage loginPage = new LoginPage(getDriver());

    @When("User enters ([^\"]*) as email")
    public void user_enters_input_as_email(String email) {
        loginPage.input_as_email(email);
    }

    @And("User enters ([^\"]*) as password")
    public void user_enters_input_as_password(String password) {
        loginPage.input_as_password(password);
    }

    @Then("^An error message should appear$")
    public void an_error_message_should_appear() throws Throwable {
        assertEquals("Invalid email address or password. Please try again.",
                loginPage.getSignInErrorMessage());
    }

    @And("^Hits sign in button$")
    public void hits_sign_in_button() throws Throwable {
        loginPage.click_sign_in_button();
    }
}
