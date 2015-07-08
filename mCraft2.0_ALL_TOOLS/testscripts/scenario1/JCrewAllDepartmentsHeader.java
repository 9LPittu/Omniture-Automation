package testscripts.scenario1;
import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 * Test Case for Department Header - Rally User Story - US9788
 */
public class JCrewAllDepartmentsHeader extends TestCase
{
	@Override
	public void setUp() {
		ElementsAction.setDriver(report);	
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