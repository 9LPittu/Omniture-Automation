package com.jcrew.steps;

import com.jcrew.page.CheckoutSignIn;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.UsersHub;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutSignInSteps extends DriverFactory {
    private CheckoutSignIn signIn = new CheckoutSignIn(getDriver());

    @When("User signs in and checks out")
    public void sign_in_and_check_out() {
    	UsersHub userHub = UsersHub.getInstance();
    	User user = null;
    	
    	try {
			 user = userHub.getUser("express", "single");
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	boolean result = signIn.enterLoginCredentials(user.getEmail(), user.getPassword());
    	if(!result){
    		signIn.signInAndCheckout(signIn.DEFAULT);
    	}
    }
    
    @When("User signs in with no default user and checks out")
    public void sign_in_no_default_and_check_out() {
    	UsersHub userHub = UsersHub.getInstance();
    	User user = null;
    	
    	try {
			 user = userHub.getUser("nonexpress", "single");
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	boolean result = signIn.enterLoginCredentials(user.getEmail(), user.getPassword());
    	if(!result){
    		signIn.signInAndCheckout(signIn.NO_DEFAULT);
    	}
    }

    @When("User signs in with multiple address user and checks out")
    public void sign_in_multiple_and_check_out() {
    	UsersHub userHub = UsersHub.getInstance();
    	User user = null;
    	
    	try {
			 user = userHub.getUser("express", "multiple");
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	boolean result = signIn.enterLoginCredentials(user.getEmail(), user.getPassword());
    	if(!result){
    		signIn.signInAndCheckout(signIn.MULTIPLE);
    	}
    }
    
    @When("User signs in with no default multiple address user and checks out")
    public void sign_in_no_default_multiple_and_check_out() {
    	UsersHub userHub = UsersHub.getInstance();
    	User user = null;
    	
    	try {
			 user = userHub.getUser("nonexpress", "multiple");
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	boolean result = signIn.enterLoginCredentials(user.getEmail(), user.getPassword());
    	if(!result){
    		signIn.signInAndCheckout(signIn.NO_DEFAULT_MULTIPLE);
    	}
    }

    @Then("Verify that title is Sign In")
    public void verify_title() {
        String title = signIn.getTitle();

        assertTrue("Title is Shopping Bag", "Sign In".equalsIgnoreCase(title));
    }

    @When("User checks out as guest")
    public void check_out_as_guest() {
        signIn.clickGuestCheckOut();
    }

    @Then("Verify Sign In Page url is ([^\"]*)")
    public void review_page_url(String url) {
        String current_url = getDriver().getCurrentUrl();
        assertTrue("Sign In page url is " + url, current_url.contains(url));
    }
}
