package com.jcrew.steps;

import com.jcrew.page.HamburgerMenu;
import com.jcrew.page.LoginPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Reporting;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class LoginPageSteps extends DriverFactory {

    private LoginPage loginPage = new LoginPage(getDriver());
    private HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());
    private Scenario scenario;


    
    @Before
    public void getScenarioObject(Scenario s){
      this.scenario = s;
    }

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

    @When("^User provides login information$")
    public void user_provides_login_information() throws Throwable {
        loginPage.enter_valid_username_and_password();
    }

    @And("^Check box is enabled$")
    public void check_box_is_enabled() throws Throwable {
        assertTrue("keep me signed in should be enabled by default", loginPage.isCheckBoxEnabled());

    }

    @And("^My Account link is present$")
    public void my_account_link_is_present() {
        if (hamburgerMenu.isCategoryPresent("WOMEN")) {

            if (loginPage.isMyAccountLinkForMobileDisplayed()) {
                assertTrue("My Account link should be present", loginPage.isMyAccountLinkForMobileDisplayed());
            }

        } else {

            hamburgerMenu.close_hamburger_menu();
            assertTrue("My Account link should be present", loginPage.isMyAccountInDesktop());
        }

    }

    @When("^User clicks on My Account link$")
    public void clicks_on_my_account_link() {
        if (loginPage.isMyAccountLinkForMobileDisplayed()) {
            loginPage.click_my_account_link_mobile();
        } else {
            loginPage.click_my_account_link_desktop();
        }
    }

    @And("^User disables check box$")
    public void user_disables_check_box() {
        loginPage.disable_checkbox();
    }

    @And("^Changes focus to password field$")
    public void changes_focus_to_password_field() throws Throwable {
        loginPage.focus_password_field();
    }

    @Then("^An error message saying ([^\"]*) should appear$")
    public void an_error_message_should_appear(String errorMessage) throws Throwable {
        assertEquals("Unable to find expected message", errorMessage, loginPage.getSignInErrorMessage());
    }

    @And("^Sign in button should be deactivated$")
    public void Sign_in_button_should_be_deactivated() throws Throwable {
        assertFalse("Sign in button should have been deactivated", loginPage.isSignInButtonEnabled());
    }

    @And("^Clicks on create new account$")
    public void clicks_on_create_new_account() throws Throwable {
        loginPage.click_create_new_account();
    }

    @And("^Clicks on forgot password link$")
    public void clicks_on_forgot_password_link() throws Throwable {
        loginPage.click_forgot_password_link();
    }
    
    @Then("click on sign in and checkout button")
    public void click_on_sign_in_and_checkout_button() throws InterruptedException{
    	loginPage.clickSignInCheckOut();
    	

    }
    
    @When("User provides mobile login credentials")
    public void user_provides_mobile_login_credentials(){
    	loginPage.enterMobileCredentials(); 
    	

    }

}

