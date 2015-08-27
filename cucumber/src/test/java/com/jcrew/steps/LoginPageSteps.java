package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.page.LoginPage;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginPageSteps extends DriverFactory {

    private LoginPage loginPage = new LoginPage(getDriver());
    private HomePage homePage = new HomePage(getDriver());

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
        if (homePage.isHamburgerMenuWomenLinkPresent()) {

            if (loginPage.isMyAccountLinkPresent()) {
                assertTrue("My Account link should be present", loginPage.isMyAccountLinkPresent());
            }

        } else {

            homePage.close_hamburger_menu();
            assertTrue("My Account link should be present", loginPage.isMyAccountInDesktop());
        }

    }

    @When("^User clicks on My Account link$")
    public void clicks_on_my_account_link() {
        loginPage.click_my_account_link();
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
}

