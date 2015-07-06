package testscripts.scenario1;
import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * Test for login with valid user credentials
 * @author Cognizant
 */
public class JCrewAllDepartmentsHeader extends TestCase
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
		
		String strDept = homePage.deptHeaderList.getText();		
		report.updateTestCaseLog("Verified","Departments are "+strDept.replace("\n", " "),Status.PASS);
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}