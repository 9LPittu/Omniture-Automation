package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

 
/**
 * Test for login with valid user credentials
 * @author Cognizant
 */
public class JCrewAllCategories extends TestCase
{
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
		
		String strCategories = homePage.categoryList.getText();		
		report.updateTestCaseLog("Verified","Departments are "+strCategories.replace("\n", " "),Status.PASS);
	}
		
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}