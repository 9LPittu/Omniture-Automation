package com.jcrew.steps;

import com.jcrew.page.LogIn;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.UsersHub;
import com.jcrew.utils.Util;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class LogInSteps extends DriverFactory {
	
	private String emailAddress;
    private String password;    
    private LogIn logIn = new LogIn(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();

    @Then("User goes to sign in page")
    public void user_in_signin_page(){
        assertTrue("User in sign in page",logIn.isSignPage());
    }

    @When("User fills user data and signs in")
    public void fills_user_data() {
        logIn.userSignIn();
        stateHolder.put("waitHomepage", true);
    }
    
    @When("User fills ([^\"]*) category data and signs in")
    public void fill_user_category_data(String userCategory){
        logIn.userSignIn(userCategory);
        stateHolder.put("waitHomepage", true);
    }


    @Then("^User get create account form$")
    public void user_get_create_account_form() {
        /*boolean isDisplayed = logIn.createAccountFormIsDisplayed();
        assertTrue("The create user form is displayed in log in page", isDisplayed);*/
    	logIn.createNewAccount();
    }

    @When("^User clicks Create An Account button$")
    public void user_clicks_create_account() {
        logIn.clickCreateAccount();
    }

    @Then("^Fields will get error messages, ignore case$")
    public void fields_will_get_error_messages_ignore_case(Map<String, String> errorMessages) {
        Set<String> fields = errorMessages.keySet();

        for (String field : fields) {
            boolean equalsIgnoringCase =
                    logIn.getErrorMessage(field).equalsIgnoreCase(errorMessages.get(field));
            assertTrue("The error message for " + field + " matches expected message",
                    equalsIgnoringCase);
        }
    }

    @Then("^Selected country is ([^\"]*)$")
    public void selected_country_is(String country) {
        boolean equalsIgnoringCase =
                logIn.getSelectedCountry().equalsIgnoreCase(country);
        assertTrue("Selected country is " + country, equalsIgnoringCase);
    }

    @Then("Selected country matches the current country context")
    public void selected_country_matches_the_current_country_context() {
        assertTrue("Selected country matches context", logIn.selectedCountryMatchesContext());
    }

    @Then("^Error message disappears from field ([^\"]*)$")
    public void error_message_is_not_displayed_for_field(String field) {
        assertFalse("Field " + field + " should not display error message", logIn.hasErrorMessage(field));
    }

    @When("^([^\"]*) field is filled with invalid data \"([^\"]*)\"$")
    public void field_is_filled_with(String field, String value) {
        logIn.fillField(field, value);
        stateHolder.put("waitHomepage", false);
    }

    @When("^([^\"]*) field is filled with ([^\"]*) data$")
    public void field_is_filled_with_new_data(String field, String userType) {
        if("new".equalsIgnoreCase(userType)) {
            logIn.fillField(field, true);
        }
        else {
            logIn.fillField(field, false);
            stateHolder.put("waitHomepage", false);
        }
    }

    @When("([^\"]*) field is filled with less than (\\d+) characters")
    public void field_is_filled_with_new_data_less_than_x_characters(String field, int characters) {
        int lessThan = Util.randomIndex(characters - 1) + 1;
        logIn.fillField(field, User.getSomePassword(lessThan));
    }

    @When("^User selects country from ([^\"]*) group$")
    public void user_selects_country_from_country_group(String country_group) {
        logIn.fillField("country", country_group);
    }

    @Then("Verify form contains international email option message")
    public void verify_form_contains_intl_email_option_message() {
        assertTrue(logIn.hasIntlEmailOptMessage());
    }

    @Then("Verify form does not contain international email option message")
    public void verify_form_does_not_contains_intl_email_option_message() {
        assertFalse(logIn.hasIntlEmailOptMessage());
    }

    @Then("Verify form contains international email option message according to country context")
    public void verify_form_contains_international_email_option_message_according_to_country_context() {
        assertTrue("Selected country matches context", logIn.checkIntlEmailOptMessageForContext());
    }

    @When("^User selects ([^\"]*) country$")
    public void user_selects_country(String value) {
        logIn.setSelectedCountryByValue(value);
    }

    @When("^Clicks on forgot password link$")
    public void clicks_on_forgot_password_link() throws Throwable {
        logIn.click_forgot_password_link();
    }
    
    @When("^User fills form and signs in$")
    public void sign_in(){    	
		//login("express", "single", logIn.DEFAULT);
		login();
		Util.wait(5000);
    }
    
    @When("^User fills form with no default user and signs in$")
    public void sign_in_no_default(){  
    	login("nonexpress", "single", logIn.NO_DEFAULT);
    }

    @When("^User fills form with multiple user and signs in$")
    public void sign_in_multiple(){
    	login("express", "multiple", logIn.MULTIPLE);
    }

    @When("^User fills form with no default multiple user and signs in$")
    public void sign_in_no_default_multiple(){
    	login("nonexpress", "multiple", logIn.NO_DEFAULT_MULTIPLE);
    }
    
    public void login() {
    	TestDataReader testDataReader =  TestDataReader.getTestDataReader();
    	String email = testDataReader.getData("user.1.email");
    	String password = testDataReader.getData("user.1.password");
    	logIn.submitUserCredentials(email,password);
    }
    public void login(String userType, String addressType, String userClassUserType){
		UsersHub userHub = UsersHub.getInstance();
		User user = null;
		
		try {
			  user = userHub.getUser(userType, addressType);			  
			  emailAddress = user.getEmail();
		      password = user.getPassword();
		      logIn.stateHolder.put("userObject", user);
		} 
		catch (SQLException e) {				
			e.printStackTrace();
		}
    	
		boolean result = logIn.submitUserCredentials(emailAddress, password);
		if(!result){
			logIn.signIn(userClassUserType);
		}

        stateHolder.put("waitHomepage", true);
    }

}
