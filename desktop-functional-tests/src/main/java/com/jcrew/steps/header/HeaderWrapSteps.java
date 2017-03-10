package com.jcrew.steps.header;

import com.jcrew.page.HeaderWrap;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import com.jcrew.utils.StateHolder;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrapSteps extends DriverFactory {
    private HeaderWrap header = new HeaderWrap(getDriver());
    private TestDataReader reader = TestDataReader.getTestDataReader();

    @When("User clicks on sign in using header")
    public void click_sign_in() {
        header.clickSignIn();
    }

    @When("User hovers over ([^\"]*)")
    public void user_hovers_over(String icon) {
        header.hoverOverIcon(icon);
    }

    @When("User signs out using header")
    public void sign_out_using_header() {
        header.goToMyDetailsDropDownMenu("Sign Out");
    }

    @When("User goes to ([^\"]*) using header")
    public void user_goes_to_using_header(String item) {
        header.goToMyDetailsDropDownMenu(item);
    }


    @Then("Dropdown should welcome user by first name")
    public void dropdown_should_welcome_user_using_first_name() {
    	StateHolder stateHolder = StateHolder.getInstance();
    	User user = stateHolder.get("userObject");
    	String firstName = user.getFirstName();
        String expectedWelcomeMessage = "Welcome, " + firstName;
        String actualWelcomeMessage = header.getWelcomeMessage();

        assertEquals("First name should match message", expectedWelcomeMessage, actualWelcomeMessage);
    }

    @Then("Verify header contains Sign In visible")
    public void verify_header_contains_sign_in_visible() {
        assertTrue("Header contains a visible sign in", header.isSignInVisible());
    }
    @And("User clicks on stores using header")
    public void user_clicks_stores_from_header(){
        header.clickStores();
    }

    @When("User clicks in bag")
    public void user_clicks_in_bag() {
        header.clickBag();
    }
    @When("User clicks in My Account")
    public void user_clicks_in_myAccount() {
        header.myAccount();
    }
    
    @When("User hovers on My Account")
    public void user_hovers_on_myAccount() {
    	header.hoverOverIcon("my account");
    }
    
    @And("Verify My Account drop down is displayed")
    public void my_account_drop_down_displayed(){
        assertTrue("My Account drop down should display",header.isMyAccountDropdownDisplayed());
    }

}
