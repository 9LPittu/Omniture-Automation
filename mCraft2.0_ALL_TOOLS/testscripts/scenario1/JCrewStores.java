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
public class JCrewStores extends TestCase
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

		//Home Page Objects - Hamburger, Department and Category
		JCrewHomePage homePage = new JCrewHomePage(webdriver);		

		homePage.getStoresLink().click();
		report.updateTestCaseLog(" Verified ","Stores Link Clicked Successfully",Status.PASS);
		
		System.out.println(homePage.getCurrentURL());	
		report.updateTestCaseLog(" Verified ","Stores Website Checked Successfully"+ homePage.getCurrentURL(),Status.PASS);
		
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