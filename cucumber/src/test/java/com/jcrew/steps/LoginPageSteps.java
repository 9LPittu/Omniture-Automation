package com.jcrew.steps;

import java.util.List;

import com.jcrew.page.HomePage;
import com.jcrew.page.LoginPage;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
   
   @Then("^User is in My Account page$")
   public void user_is_in_My_Account_page() throws Throwable {
         
         assertTrue("User should be in My Account Page", loginPage.isInAccountPage());
         loginPage.printNavigationList();
         assertTrue("My Account Page should have My details link",loginPage.isMyDetailsPresent().equals("MY DETAILS"));
         assertTrue("My Account Page should have Email Preferences link",loginPage.isEmailPreferencePresent().equals("EMAIL PREFERENCES"));
         assertTrue("My Account Page should have Catalog Preferences link",loginPage.isCatalogPreferencePresent().equals("CATALOG PREFERENCES"));
         assertTrue("My Account Page should have Payment Methods link",loginPage.isPaymentMethodPresent().equals("PAYMENT METHODS"));
         assertTrue("My Account Page should have Gift Card Balance link",loginPage.isGiftCardBalancePresent().equals("GIFT CARD BALANCE"));
         assertTrue("My Account Page should have Address Book link",loginPage.isAddressBookPresent().equals("ADDRESS BOOK"));
         assertTrue("My Account Page should have Order History link",loginPage.isOrderHistoryPresent().equals("ORDER HISTORY"));
         
         assertTrue("User name should be displayed",loginPage.isUserNamePresent().contains("nusrat"));
         }
   
   @And("^User closes the browser$") 
    public void user_closes_the_browser() {
       loginPage.close_browser();
   }
   
   @And("^My Account link is present$")
   public void my_account_link_is_present() {
       if (homePage.isHamburgerMenuWomenLinkPresent()) {
           if (loginPage.isMyAccountLinkPresent())
               assertTrue("My Account link should be present",loginPage.isMyAccountLinkPresent());
       }     
       else  
               homePage.close_hamburger_menu();
               //close hamburger 
         //check for my account in desktop version
               assertTrue("My Account link should be present",loginPage.isMyAccountInDesktop());
            
       } 
   
   
   @And("^User comes back to homepage$") 
   public void user_opens_the_browser() {
       
       loginPage.open_browser();
       
   }
   
   @When("^User clicks on My Account link$")
   public void clicks_on_my_account_link()  {
       loginPage.click_my_account_link();
   }
   
  // @Then("^User is on already registered page$")
//   public void verify_already_registered_page() {
  //    assertTrue("User should be in already registered page",loginPage.user_already_registered().equals("I'm already registered"));
  // }
   
   @And("^User disables check box$")
   public void user_disables_check_box()  {
       loginPage.disable_checkbox();
   }
   
   @ And("^User navigates to home page$")
   public void user_navigates_to_homepage()  {
       loginPage.navigate_to_homepage();
       
   }
}

