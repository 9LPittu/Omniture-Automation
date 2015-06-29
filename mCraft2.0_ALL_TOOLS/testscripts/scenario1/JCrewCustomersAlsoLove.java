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
public class JCrewCustomersAlsoLove extends TestCase
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
		JCrewHomePage homePage = new JCrewHomePage();		
		ElementsAction.act(homePage.hamburgerMenu,"click", "");				
		homePage.deptClick(dataTable.getData("General_Data","DepartmentName"));			
		homePage.categoryClick(dataTable.getData("General_Data","CategoryName"));
		
		ElementsAction.callMeToWait(1000);
		JCrewSubCategoryPage jcrewSubCategoryPage = new JCrewSubCategoryPage();
		int subCategory = Integer.parseInt(dataTable.getData("General_Data","Sub-Category"));
		int productName = Integer.parseInt(dataTable.getData("General_Data","Product"));
		ElementsAction.act(jcrewSubCategoryPage.getProductToSelect(subCategory,productName), "click", "");
							
		JCrewProductDetailPage jcrewPDP = new JCrewProductDetailPage();		
		ElementsAction.act(jcrewPDP.size,"click","");				
		ElementsAction.act(jcrewPDP.addToBagBtn,"click","");				
		ElementsAction.callMeToWait(2000);
		ElementsAction.act(jcrewPDP.customersAlsoLove,"click","");		
		ElementsAction.act(jcrewPDP.size,"click","");						
		ElementsAction.act(jcrewPDP.addToBagBtn,"click","");										
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}