package com.jcrew.steps;

import com.jcrew.page.MyAccountPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MyAccountPageSteps extends DriverFactory {

    private final MyAccountPage myAccountPage = new MyAccountPage(getDriver());

    @Then("^User is in My Account home page$")
    public void user_is_in_My_Account_page() throws Throwable {
        assertTrue(Util.getSelectedCountryName() + "User should be in My Account Page", myAccountPage.isInAccountPage());
    }

    @And("^Verifies page displays My Account title$")
    public void Verifies_page_displays_My_Account_title() throws Throwable {
        assertEquals("Header is not what is expected", "MY ACCOUNT", myAccountPage.getMyAccountHeader());
    }

    @And("Validate ([^\"]*) link ([^\"]*) in My Account Page")
    public void verify_link_displayed_on_myAccount_page(String link,String visible){
        if("notVisible".equalsIgnoreCase(visible)){
            assertTrue(link +" link is not visible for user ",myAccountPage.isMenuLinkNotPresent(link));
        }else{
            assertFalse(link +" link is visible for user ",myAccountPage.isMenuLinkNotPresent(link));
        }

    }

    @Then("^User clicks on ([^\"]*) link in My Account Page$")
    public void user_clicks_on_link_in_my_account_page(String link) throws Throwable {
        myAccountPage.click_menu_link(link);
    }



    @Then("^User selects an order listed for review$")
    public void user_selects_an_order_listed_for_review() throws Throwable {
        myAccountPage.click_order_for_review();
    }
    
    @And("^click on \"([^\"]*)\" link in My Account page$")
    public void click_my_account_link(String myAccountLinkName){
    	myAccountPage.click_menu_link(myAccountLinkName);
    }
    
    @And("^delete non-default addresses$")
    public void delete_non_default_addresses(){
    	myAccountPage.deleteNonDefaultAddresses();
    }
    
    @And("^delete non-default credit cards$")
    public void delete_non_default_credit_cards(){
    	myAccountPage.deleteNonDefaultCreditCards();
    }
}