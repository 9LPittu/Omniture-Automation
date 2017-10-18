package com.jcrew.steps;

import com.jcrew.page.Content;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ContentSteps extends DriverFactory {

	Content contentTest = new Content();
	@Given("User goes to Launching page")
    public void userGoesToHomePage() throws Exception{
        contentTest.goesToHomePage();
    }
	
	@Then("Get the page load time for the url") 
	public void getPageLoadTime() throws Exception{
		contentTest.readAndWriteResultsIntoExcel();
	}
}
