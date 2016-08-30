package com.jcrew.steps;

import com.jcrew.page.MyAccount;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class MyAccountSteps extends DriverFactory {
    MyAccount myAccount = new MyAccount(getDriver());
    StateHolder stateHolder = StateHolder.getInstance();

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
        User user = (User) stateHolder.get("signedUser");

        Map<String, String> userDetails = myAccount.getUserDetails();
        String user_country = user.getCountry();

        boolean equalsIgnoreCase = user_country.equalsIgnoreCase(userDetails.get(myAccount.USER_DETAILS_COUNTRY));

        assertEquals("First name matches", userDetails.get(myAccount.USER_DETAILS_FIRST_NAME), user.getFirstName());
        assertEquals("Last name matches", userDetails.get(myAccount.USER_DETAILS_LAST_NAME), user.getLastName());
        assertEquals("Email matches", userDetails.get(myAccount.USER_DETAILS_EMAIL), user.getEmail());
        assertTrue("Country matches", equalsIgnoreCase);
    }

    @Then("Verify user is in Order History page")
    public void user_is_in_order_history_page() {
        assertTrue("User is in order history page",myAccount.isOrderHistoryPage());
    }

    @Then("^User clicks on ([^\"]*) link in My Account Page$")
    public void user_clicks_on_link_in_my_account_page(String link) throws Throwable {
        myAccount.click_menu_link(link);
    }

    @Then("^User should be in ([^\"]*) menu link page$")
    public void User_should_be_in_page(String page) throws Throwable {
        assertTrue("User should have been in menu link " + page,
                myAccount.isInMenuLinkPage(page));
    }

}
