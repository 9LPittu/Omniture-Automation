package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

 
/**
 * @author Cognizant
 */
public class JCrewAllDepartments extends TestCase
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
									
		String strDept = homePage.deptList.getText();		
		report.updateTestCaseLog("Verified","Departments are "+strDept.replace("\n", " "),Status.PASS);
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}