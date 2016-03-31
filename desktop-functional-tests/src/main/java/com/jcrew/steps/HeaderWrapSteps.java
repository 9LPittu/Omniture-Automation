package com.jcrew.steps;

import com.jcrew.page.HeaderWrap;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrapSteps extends DriverFactory{
    HeaderWrap header = new HeaderWrap(getDriver());

    @When("User clicks on sign in using header")
    public void click_sign_in(){
        header.clickSignIn();
    }

    @When("User hovers in My Account dropdown")
    public void hover_in_my_account_dropdown(){
        header.hoverMyAccount();
    }

    @When("User signs out using header")
    public void sign_out_using_header(){
        header.goToDropDownMenu("Sign Out");
    }

    @When("User goes to ([^\"]*) using header")
    public void user_goes_to_using_header(String item){
        header.goToDropDownMenu(item);
    }

    @Then("Dropdown should welcome user by first name")
    public void dropdown_should_welcome_user_using_first_name() {
        String expectedWelcomeMessage= "Welcome, " + User.getUser().getFirstName();
        String actualWelcomeMessage = header.getWelcomeMessage();

        assertEquals("First name should match message", expectedWelcomeMessage, actualWelcomeMessage);
    }

    @Then("Verify header contains Sign In visible")
    public void verify_header_contains_sign_in_visible(){
        assertTrue("Header contains a visible sign in",header.isSignInVisible());
    }
}
