package com.jcrew.steps;

import com.jcrew.page.Content_Madewell;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ContentSteps_madewell extends DriverFactory {

	Content_Madewell contentTest = new Content_Madewell();
	@Given("User goes to madewell Launching page")
    public void userGoesToHomePage() throws Exception{
        contentTest.goesToHomePage();
    }
	
	@Then("Get the page load time for the madewell url") 
	public void getPageLoadTime() throws Exception{
		contentTest.readAndWriteResultsIntoExcel();
	}
}
