package testscripts.scenario1;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
public class JCrewAllDepartmentsHeader extends TestCase
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
		
		JCrewHomePage homePage = new JCrewHomePage();		
		homePage.getDeptHeaderList();
		
		for(WebElement checkBox:homePage.getDeptHeaderList()){
			int i=0;
			checkBox = homePage.getDeptHeaderList().get(i);			
			report.updateTestCaseLog("Verified","Department Found in header are " +checkBox.getText(),Status.PASS);
		}	
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}	
	
}