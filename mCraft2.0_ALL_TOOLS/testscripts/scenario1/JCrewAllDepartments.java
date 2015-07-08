package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

 
/**
 * @author Cognizant
 * Test Case for All Categories - Rally User Story - US9345
 */
public class JCrewAllDepartments extends TestCase
{
	@Override
	public void setUp() {
		//Nothing to do	
	}
	
	@Override
	public void executeTest()
	{		
		JCrewHomePage homePage = new JCrewHomePage();		
		ElementsAction.act(homePage.hamburgerMenu, "click", "");						
									
		String strDept = homePage.deptList.getText();		
		report.updateTestCaseLog("Verified","Departments are "+strDept.replace("\n", " "),Status.PASS);
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}