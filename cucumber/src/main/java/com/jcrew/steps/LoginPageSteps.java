package com.jcrew.steps;

import com.jcrew.page.HamburgerMenu;
import com.jcrew.page.LoginPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class LoginPageSteps extends DriverFactory {

    private final LoginPage loginPage = new LoginPage(getDriver());
    private final HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());

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
        assertTrue("My Account link should be present", loginPage.isMyAccountLinkForMobileDisplayed());
    }

    @When("^User clicks on My Account link$")
    public void clicks_on_my_account_link() {
        loginPage.click_my_account_link_mobile();
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

    @And("^Login page is loaded$")
    public void login_page_is_loaded() throws Throwable {
        assertTrue("Login page was not loaded properly", loginPage.isPageLoaded());
    }
    
    @And("^click on CHECK OUT AS A GUEST button$")    
    public void click_checkout_as_guest_button() throws InterruptedException{    	  	
    	loginPage.clickCheckoutAsGuest();
    }
    
    @And("^enter email address as \"([^\"]*)\" on sign in page$")
    public void enter_email_address_on_sign_in_page(String emailAddress){
    	loginPage.enterEmailAddressOnSignInPage(emailAddress);
    }
    
    @And("^enter password as \"([^\"]*)\"$")
    public void enter_password_on_sign_in_page(String password){
    	loginPage.enterPasswordOnSignInPage(password);
    }
    
    @And("^click on SIGN IN & CHECK OUT button$")
    public void enter_sign_in_and_check_out_button(){
    	loginPage.click_signInAndCheckOut();
    }

    @And("^Registration benefits copy is displayed as ([^\"]*)$")
    public void verify_benefits_copy_message_is_displayed(String expected) {
        assertEquals("Registration benefits message is not as expected",expected,loginPage.getRegBenefitsCopyMsg());
    }
    @And("^First name field is displayed in registration section$")
    public void verify_first_name_field_is_present() {
        assertTrue("First name field is not present",loginPage.isFirstNameFieldDisplayed());
    }

    @And("^First name field allows maximum ([^\"]*) characters$")
    public void verify_max_length_value_for_first_name(String expected) {
        assertEquals("First name field max charaters allowed is not as expected ",expected,loginPage.getMaxCharsForFirstName());
    }

    @And("^User clicks on create an account button$")
    public void clicks_on_create_an_account_button() {
        loginPage.click_create_an_account_button();
    }

    @And("^An error message saying ([^\"]*) should appear under ([^\"]*) field")
    public void  verify_error_message(String errorMessage, String fieldLabel) {
        assertEquals("Unable to find expected message", errorMessage, loginPage.getErrorMessage(fieldLabel));
    }

}