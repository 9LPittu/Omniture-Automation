package com.jcrew.steps;

import com.jcrew.page.ReviewPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Reporting;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import static org.junit.Assert.assertTrue;

public class ReviewPageSteps extends DriverFactory {

    private final ReviewPage reviewPage = new ReviewPage(getDriver());
    
    private Scenario scenario;
    private Reporting reporting = new Reporting();
    
    @Before
    public void getScenarioObject(Scenario s){
      this.scenario = s;
    }

    @Then("^Clicks on place your order$")
    public void user_places_its_order() throws Throwable {
        reviewPage.user_places_its_order();
    }

    @And("^Validates billing section is present in review page$")
    public void Validates_billing_section_is_present_in_review_page() throws Throwable {
        assertTrue("Billing section should be displayed", reviewPage.isBillingSectionDisplayed());
    }

    @And("^Inputs credit card security code$")
    public void inputs_credit_card_security_code() throws Throwable {
       reviewPage.input_credit_card_security_code();
    }

    @And("^Validates shipping section is present in review page$")
    public void validates_shipping_section_is_present_in_review_page() throws Throwable {
        assertTrue("Shipping section should be displayed", reviewPage.isShippingSectionDisplayed());
    }
    
    @Then("verify shipping address section is displayed")
    public void verify_shipping_address_section(){
    	assertTrue("Shipping address section should be displayed", reviewPage.isShippingDetailsSectionDisplayed());
    	
    	reporting.takeScreenshot(scenario);
    }
    
    @Then("verify billing address section is displayed")
    public void verify_billing_address_section(){
    	assertTrue("Billing address section should be displayed", reviewPage.isBillingDetailsSectionDisplayed());
    	
    	reporting.takeScreenshot(scenario);
    }
    
    @And("enter credit card security code as \"([^\"]*)\"")
    public void enter_security_code(String securityCode){
    	reviewPage.enter_credit_card_security_code(securityCode);
    	
    	reporting.takeScreenshot(scenario);
    }
    
    @And("click on place your order button")
    public void click_place_your_order() throws InterruptedException{
    	reviewPage.clickPlaceYourOrder();
    	
    	reporting.takeScreenshot(scenario);
    }
}
