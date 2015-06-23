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
public class TestAppium extends TestCase
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
		ElementsAction.Name("q", "Click", "");
		ElementsAction.Name("q", "entertext", "Hi hello");
		ElementsAction.Name("btnG", "Click", "");
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}