package com.jcrew.steps;

import com.jcrew.page.Content_Jcrew;
import com.jcrew.page.TrainingOrder;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ContentSteps_Jcrew extends DriverFactory {

	Content_Jcrew contentTest = new Content_Jcrew();
	TrainingOrder order = new TrainingOrder();
	@Given("User goes to Launching page")
    public void userGoesToHomePage() throws Exception{
        contentTest.goesToHomePage();
    }
	
	@Then("Get the page load time for the url") 
	public void getPageLoadTime() throws Exception{
		//contentTest.readAndWriteResultsIntoExcel();
		order.getPromoOrder();
		
	}
}
