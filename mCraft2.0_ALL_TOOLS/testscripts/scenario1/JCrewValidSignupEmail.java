package testscripts.scenario1;
import java.io.IOException;

import org.testng.annotations.Test;

import supportlibraries.DriverScript;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

import com.cognizant.framework.IterationOptions;
import com.cognizant.framework.Status;


/**
 * Test for login with valid user credentials
 * @author Cognizant
 */
public class JCrewValidSignupEmail extends TestCase
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
		
		homePage.getEmailSetupTextBox(report);
		report.updateTestCaseLog("Verified","Email Entered Successfully",Status.PASS);
		
		homePage.getSignup().click();
		report.updateTestCaseLog("Verified","Email Signup Clicked Successfully",Status.PASS);
		
		this.sleep(5);
		
		if(homePage.getEmailSuccess().getText().length()>0)
		report.updateTestCaseLog("Verified","Invalid Email Address Verified:" + homePage.getEmailSuccess().getText(),Status.PASS);
		
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