package testscripts.scenario1;
import java.io.IOException;



import org.testng.annotations.Test;

import com.cognizant.framework.IterationOptions;
import com.cognizant.framework.Status;


import supportlibraries.DriverScript;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;



/**
 * Test for login with valid user credentials
 * @author Cognizant
 */
public class JCrewSearch extends TestCase
{
	
	
	@Test()
	public void runTC1() throws IOException
	{ 
		// Modify the test parameters as required
		
	    	
	     
	    testParameters.setCurrentTestDescription("Test for login with valid user credentials");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		
		//testParameters.setBrowser(MobilePlatform.Web);
		
		driverScript = new DriverScript(testParameters);
		driverScript.setLinkScreenshotsToTestLog(true);
		driverScript.driveTestExecution(); 
		
		
	}
	
	@Override
	public void setUp()
	{	
		ElementsAction.setDriver(report);
		report.updateTestCaseLog("Invoke Application", "Invoke the application under test  ", Status.DONE);
	}
	
	@Override
	public void executeTest()
	{
		

		JCrewHomePage homePage = new JCrewHomePage(webdriver);
		
		homePage.menuSearch.click();
		report.updateTestCaseLog("Verified","Search Menu Clicked Successfully",Status.PASS);
		
		String searchStr = dataTable.getData("General_Data","SearchKeyword");
		homePage.getSearchTextBox(searchStr);	
		report.updateTestCaseLog("Verified","Search Text Entered Successfully",Status.PASS);
		
		homePage.searchIcon.click();
		report.updateTestCaseLog("Verified","Search Icon Clicked Successfully",Status.PASS);						

				
		this.sleep(4);
	}
	
	public void sleep(int seconds) 
	{
	    try {
	        Thread.sleep(seconds * 1000);
	    } catch (InterruptedException e) {

	    }
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}