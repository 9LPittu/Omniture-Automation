package com.jcrew.steps;

import com.jcrew.page.Content_Jcrew;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ContentSteps_Factory extends DriverFactory {

	Content_Jcrew factory = new Content_Jcrew();
	@Given("User goes to factory Launching page")
    public void userGoesToHomePage() throws Exception{
		factory.goesToHomePage();
    }
	
	@Then("Get the page load time for the factory url") 
	public void getPageLoadTime() throws Exception{
		factory.readAndWriteResultsIntoExcel();
	}
}
