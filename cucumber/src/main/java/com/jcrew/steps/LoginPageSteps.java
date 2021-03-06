package com.jcrew.steps;

import com.jcrew.page.LoginPage;
import com.jcrew.pojo.User;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.UsersHub;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

import java.sql.SQLException;

public class LoginPageSteps extends DriverFactory {

    private final LoginPage loginPage = new LoginPage(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    private String emailAddress;
    private String password;

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


    @When("^User provides ([^\"]*) category login information$")
    public void user_provides_login_information(String userCategory) throws Throwable {
        loginPage.enter_valid_username_and_password(userCategory);
    }

    @When("^User provides login information$")
    public void user_provides_login_information() throws Throwable {
        loginPage.enter_valid_username_and_password();
    }

    @And("^Check box is enabled$")
    public void check_box_is_enabled() throws Throwable {
        assertTrue(Util.getSelectedCountryName() + "keep me signed in should be enabled by default", loginPage.isCheckBoxEnabled());

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
    
    @And("^enter ([^\"]*) email address on sign in page$")
    public void enter_email_address_on_sign_in_page(String emailAddress){
    	loginPage.enterEmailAddressOnSignInPage(emailAddress);
    }
    
    @And("^enter ([^\"]*) password$")
    public void enter_password_on_sign_in_page(String password){
    	loginPage.enterPasswordOnSignInPage(password);
    }
    
    @And("^enter login information on sign in page$")
    public void enter_login_information_on_sign_in_page(){
    	loginPage.enterLoginInformationOnSignInPage();
    }
    
    @And("^click on SIGN IN & CHECK OUT button$")
    public void enter_sign_in_and_check_out_button(){
    	loginPage.click_signInAndCheckOut();
    }

    @And("^Registration benefits copy is displayed as ([^\"]*)$")
    public void verify_benefits_copy_message_is_displayed(String expected) {
        assertEquals("Registration benefits message is not as expected",expected,loginPage.getRegBenefitsCopyMsg());
    }

    @And("^([^\"]*) field is displayed in registration section and max ([^\"]*) characters allowed$")
    public void verify_input_field_is_present(String field, String chars) {
        assertTrue("Field with expected max chars should be displayed",loginPage.isFieldWithMaxCharsAllowedDisplayed(field, chars));
    }

    @And("^User clicks on create an account button$")
    public void clicks_on_create_an_account_button() {
        loginPage.click_create_an_account_button();
    }

    @And("^An error message saying ([^\"]*) should appear under ([^\"]*) field")
    public void verify_error_message(String errorMessage, String fieldLabel) {
        assertEquals("Unable to find expected message", errorMessage, loginPage.getErrorMessage(fieldLabel));
    }

    @And("^Enter ([^\"]*) as ([^\"]*) in create account section$")
    public void enter_input_in_the_input_field(String input, String field)  {
        loginPage.enter_input(input,field);
    }

    @Then("^Error message ([^\"]*) is displayed")
    public void verify_invalid_email_error_message(String msg) {
        assertEquals("Error message under email field should be displayed", msg, loginPage.getEmailErrorMessage());
    }

    @And("^Country selection list box is displayed$")
    public void verify_country_list_box_is_displayed() {
        assertTrue("Country list box should be displayed", loginPage.isCountryListBoxDisplyed());

    }

    @And("^([^\"]*) is selected as default value$")
    public void verify_default_value_for_country_list_box(String defaultcountry) {
         assertEquals("Default country to be selected not as expected", defaultcountry.toLowerCase(), loginPage.getDefaultCountrySelected().toLowerCase());

    }

    @And("User can choose top10 countries from the country list box")
    public void verify_user_choose_top10_country() {
        assertTrue("coresponding country should be displayed",loginPage.select_top10_country_and_verify_corresponding_flag_is_displayed());

    }

    @And("User can choose any country from the country list box")
    public void verify_user_choose_any_country() {
        loginPage.select_each_country_and_verify_corresponding_flag_is_displayed();

    }

    @And("^selects any country from the country list$")
    public void select_any_random_country_from_the_list() {
        loginPage.select_any_random_country();
    }

    @And("^Verify opt checkbox not displayed for USA$")
    public void verify_opt_checkbox_not_displayed_when_selected_usa() {
        assertFalse("opt to receive marketing communications checkbox should not be displayed", loginPage.isOptCheckBoxDisplayed());
    }
    
    @And("^user should see birthday section after password field$")
    public void verify_user_should_see_birthday_section_after_password_field(){
    	assertTrue("user should see birthday section after password field", loginPage.isBirthdaySectionDisplayedAfterPasswordField());
    }
    
    @And("^user should see label text as \'([^\"]*)\' in create account form$")
    public void user_should_see_label_text_in_create_account_form(String expectedLabelText){
    	assertTrue("user should see '" + expectedLabelText + "' in create account form", loginPage.isLabelTextDisplayedInCreateAccountForm(expectedLabelText));
    }
    
    @And("^([^\"]*) dropdown should have value as \'([^\"]*)\'$")
    public void verify_dropdown_value(String dropdownName, String expectedDropdownValue){
    	assertTrue(dropdownName + " dropdown should have value as " + expectedDropdownValue, loginPage.isDropdownDisplaysExpectedValue(dropdownName, expectedDropdownValue));
    }
    
    @And("^([^\"]*) dropdown should have options same as calendar$")
    public void verify_dropdown_values(String dropdownName){
    	assertTrue(dropdownName + " dropdown should have options same as calendar " + dropdownName + "s", loginPage.isDropdownValuesMatchesCalendarValues(dropdownName));
    }
    
    @And("^user selects ([^\"]*) value from ([^\"]*) dropdown$")
    public void user_selects_value_from_dropdown(String dropdownValue, String dropdownName){
    	loginPage.selectValuefromDropdown(dropdownName, dropdownValue);
    }
    
    @Then("^user should see the error message as \'([^\"]*)\'$")
    public void user_should_see_error_message(String expectedErrorMessage){
    	loginPage.isCorrectErrorMessageDisplayed(expectedErrorMessage);
    }
    
    @And("^login with rewards user credentials$")
    public void login_with_reward_user_credentials(){
    	loginPage.enterRewardUserCredentials();
    }
    
    @When("^User fills form and signs in$")
    public void sign_in(){
    	stateHolder.put("atpAddressType","regular");
		login("express", "single", loginPage.DEFAULT);
    }
    
    @When("^User fills form with no default user and signs in$")
    public void sign_in_no_default(){
    	
    	stateHolder.put("atpAddressType","regular");
    	login("nonexpress", "single", loginPage.NO_DEFAULT);
    }

    @When("^User fills form with multiple user and signs in$")
    public void sign_in_multiple(){
    	login("express", "multiple", loginPage.MULTIPLE);
    }

    @When("^User fills form with no default multiple user and signs in$")
    public void sign_in_no_default_multiple(){
    	login("nonexpress", "multiple", loginPage.NO_DEFAULT_MULTIPLE);
    }
    
    @And("^User fills form with fpo user and signs in$")
    public void sign_in_fpo_user(){
    	stateHolder.put("atpAddressType",loginPage.FPO);
    	stateHolder.put("isRestrictedAddress", true);
    	login("nonexpress", loginPage.FPO, loginPage.FPO);
    }
    
    @And("^User fills form with apo user and signs in$")
    public void sign_in_apo_user(){
    	stateHolder.put("atpAddressType",loginPage.APO);
    	stateHolder.put("isRestrictedAddress", true);
    	login("nonexpress", loginPage.APO, loginPage.APO);
    }
    
    @And("^User fills form with po box user and signs in$")
    public void sign_in_po_box_user(){
    	stateHolder.put("atpAddressType",loginPage.POBOX);
    	stateHolder.put("isRestrictedAddress", true);
    	login("nonexpress", loginPage.POBOX, loginPage.POBOX);
    }
    
    
    @And("^User fills form with hawaii user and signs in$")
    public void sign_in_hawaii_user(){
    	stateHolder.put("atpAddressType",loginPage.HAWAII);
    	stateHolder.put("isRestrictedAddress", true);
    	login("nonexpress", loginPage.HAWAII, loginPage.HAWAII);
    }
    
    public void login(String userType, String addressType, String userClassUserType){
		UsersHub userHub = UsersHub.getInstance();
		User user = null;
		
		try {
			  user = userHub.getUser(userType, addressType);			  
			  emailAddress = user.getEmail();
		      password = user.getPassword();
		      loginPage.stateHolder.put("userObject", user);
		} 
		catch (SQLException e) {				
			e.printStackTrace();
		}
    	
		boolean result = loginPage.submitUserCredentials(emailAddress, password);
		if(!result){
			loginPage.signIn(userClassUserType);
		}		
    }
}