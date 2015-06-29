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
public class JCrewInvalidSignupEmail extends TestCase
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
		
		String emailText = dataTable.getData("General_Data","EmailID");		
		ElementsAction.act(homePage.emailTextBox, "entertext", emailText);
		
		ElementsAction.act(homePage.signupBtn, "click", "");				
		
		ElementsAction.callMeToWait(10);	
						
		ElementsAction.act(homePage.emailFail, "verifytext", "Please enter a valid email address.");			
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}