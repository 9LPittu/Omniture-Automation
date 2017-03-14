package com.jcrew.steps;

import com.jcrew.page.MyAccount;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

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


    @Then("Verify user is in Order History page")
    public void user_is_in_order_history_page() {
        assertTrue("User is in order history page",myAccount.isOrderHistoryPage());
    }

    @And("Verify ([^\"]*) reward link for ([^\"]*) user in My account page")
    public void reward_link_displayed_for_user(String link, String userCategory) {
        User signedInUser = (User ) stateHolder.get("signedUser");
        assertEquals(link + " link displayed for "+signedInUser.getEmail()+" user and category " + userCategory,
                myAccount.shouldRewardDisplayed(userCategory),myAccount.isMenuLinkPresent(link));
    }


    @Then("User clicks on ([^\"]*) reward link from My Account Page")
    public void user_clicks_on_reward_link_in_my_account_page(String link) throws Throwable {
        myAccount.click_reward_link(link);
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
