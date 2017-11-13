package com.jcrew.steps;

import com.jcrew.page.Content_Jcrew;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ContentSteps_madewell extends DriverFactory {

	Content_Jcrew madewell = new Content_Jcrew();
	@Given("User goes to madewell Launching page")
    public void userGoesToHomePage() throws Exception{
		madewell.goesToHomePage();
    }
	
	@Then("Get the page load time for the madewell url") 
	public void getPageLoadTime() throws Exception{
		madewell.readAndWriteResultsIntoExcel();
	}
}
