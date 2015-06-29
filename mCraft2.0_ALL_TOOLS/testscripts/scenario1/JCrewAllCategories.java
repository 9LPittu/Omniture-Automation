package testscripts.scenario1;
import java.io.IOException;

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
public class JCrewAllCategories extends TestCase
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
		ElementsAction.act(homePage.hamburgerMenu, "click", "");
		
		homePage.deptClick(dataTable.getData("General_Data","DepartmentName"));
		
		
		for(WebElement checkBox:homePage.categoryList)
		{
			int i=0;
			checkBox = homePage.categoryList.get(i);	
			System.out.println(homePage.categoryList.get(i));
			report.updateTestCaseLog("Verified","Categories Found are " +checkBox.getText().toString(),Status.PASS);
		}	
	}
		
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}	
	
}