package com.jcrew.steps.header;

import com.jcrew.page.account.IMyAccount;
import com.jcrew.page.account.MyAccount;
import com.jcrew.page.header.HeaderWrap;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrapSteps extends DriverFactory {
    private HeaderWrap header = new HeaderWrap(getDriver());

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

    @When("User goes to My Details from header")
    public void my_details_from_header() {
        header.goToMyDetailsDropDownMenu("My Account");

        IMyAccount myAccount = MyAccount.getAccountMain(getDriver());
        myAccount.click_menu_link("My Details");

    }

    @Then("Dropdown should welcome user")
    public void dropdown_should_welcome_user_using_first_name() {
    	StateHolder stateHolder = StateHolder.getInstance();
    	User user = stateHolder.get("userObject");

    	String expectedWelcomeMessage = user.getFirstName() + user.getLastName() + "\nsign out";
        String actualWelcomeMessage = header.getWelcomeMessage();

        assertEquals("Dropdown welcomes user",
                expectedWelcomeMessage, actualWelcomeMessage);
    }

    @Then("Verify header contains Sign In visible")
    public void verify_header_contains_sign_in_visible() {
        assertTrue("Header contains a visible sign in", header.isSignInVisible());
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
