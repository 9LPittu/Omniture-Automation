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
		JCrewHomePage homePage = new JCrewHomePage();
		
		ElementsAction.act(homePage.menuSearch, "click", "");
		
		ElementsAction.act(homePage.searchClose, "click", "");
						
		ElementsAction.act(homePage.menuSearch, "click", "");
		
		ElementsAction.callMeToWait(1000);
		
		String searchStr = dataTable.getData("General_Data","SearchKeyword");
		ElementsAction.act(homePage.searchTextBox, "entertext", searchStr);
				
		ElementsAction.act(homePage.searchIcon, "click", "");
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}