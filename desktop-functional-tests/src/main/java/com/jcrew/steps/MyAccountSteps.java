package com.jcrew.steps;

import com.jcrew.page.MyAccount;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class MyAccountSteps extends DriverFactory {
    MyAccount myAccount = new MyAccount(getDriver());

    @When("User goes to ([^\"]*) using My Account menu")
    public void user_goes_to_option_using_my_account_menu(String option){
        myAccount.clickInMenuOption(option);
    }

    @Then("Verify user is in My Account main page")
    public void user_is_in_my_account_page(){
        assertTrue("User is in My Account page", myAccount.isMyAccountMainPage());
    }

    @Then("([^\"]*) user information should match My Details page")
    public void sign_in_user_information_should_match_my_details_page(String userType) {
        User user;

        if("new".equalsIgnoreCase(userType))
            user = User.getFakeUser();
        else
            user = User.getUser();

        Map<String, String> userDetails = myAccount.getUserDetails();
        boolean equalsIgnoreCase = user.getCountry().equalsIgnoreCase(userDetails.get(myAccount.USER_DETAILS_COUNTRY));

        assertEquals("First name matches", userDetails.get(myAccount.USER_DETAILS_FIRST_NAME), user.getFirstName());
        assertEquals("Last name matches", userDetails.get(myAccount.USER_DETAILS_LAST_NAME), user.getLastName());
        assertEquals("Email matches", userDetails.get(myAccount.USER_DETAILS_EMAIL), user.getEmail());
        assertTrue("Country matches", equalsIgnoreCase);
    }

    @Then("Verify user is in Order History page")
    public void user_is_in_order_history_page() {
        assertTrue("User is in order history page",myAccount.isOrderHistoryPage());
    }
}
