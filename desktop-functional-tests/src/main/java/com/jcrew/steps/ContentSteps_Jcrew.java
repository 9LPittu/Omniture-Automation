package com.jcrew.steps;

import java.util.List;

import com.jcrew.page.ContentRegression;
//import com.jcrew.page.Content_Jcrew;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import gherkin.formatter.model.DataTableRow;

public class ContentSteps_Jcrew extends DriverFactory {
	//Content_Jcrew contentTest = new Content_Jcrew();
	ContentRegression contentTest = new ContentRegression();
	//TrainingOrder order = new TrainingOrder();
	@Given("User goes to Launching page")
    public void userGoesToHomePage() throws Exception{
        contentTest.goesToHomePage();
    }
	
	@Then("Get the page load time for the url") 
	public void readTheUrlFromSheet(DataTable sheetNames) throws Exception{
		List<DataTableRow> row = sheetNames.getGherkinRows();
        DataTableRow selectedRow = row.get(Util.randomIndex(row.size()));
        String sheetName = selectedRow.getCells().get(0);
		contentTest.readAndWriteResultsIntoExcel(sheetName);
		//order.getPromoOrder();
	}

}
