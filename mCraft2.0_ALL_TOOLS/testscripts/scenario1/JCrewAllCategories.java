package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

 
/**
 * @author Cognizant
 * Test Case for All Categories - Rally User Story - US9345
 */
public class JCrewAllCategories extends TestCase
{
	@Override
	public void setUp() {
		ElementsAction.setDriver(report);	
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