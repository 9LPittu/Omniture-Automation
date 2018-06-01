package com.jcrew.steps;

import java.awt.AWTException;

import com.jcrew.page.ccPage;
import com.jcrew.utils.DriverFactory;


import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class ContactCenter extends DriverFactory {
	
	ccPage cc = new ccPage(getDriver());
	
	@And("^User goes to cc homepage$")
	    public void goToContactCenterLoginPage() {
		 	cc.goToHomePage();
	 }

	 @And("^Login to cc$")
	    public void loginToCC() {
		 	cc.loginToCC();
	 }
	 
	 @And("^User selects the customer$")
	    public void userSelectsTheCustomer() {
		 	cc.selectCustomer();
	 }
	 
	 @And("^User Select Brand type$")
	    public void selectBrandType() {
		 	cc.selectBrand();
	 }
	 
	 
	 @Then("^User Creates New Order$")
	    public void createNewOrder() throws Exception {
		 	cc.createNewOrder();
	 }
	 
	@Then("^get The Order Number$")
	 public void getOrderNumber() throws AWTException {
		 cc.submitOrder();
	 }

}