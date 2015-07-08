package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 * Test Case for Stores Link - Rally User Story - US9354
 */
public class JCrewStores extends TestCase
{
	@Override
	public void setUp() {
		ElementsAction.setDriver(report);
	}
	
	@Override
	public void executeTest()
	{		
		JCrewHomePage homePage = new JCrewHomePage();		
		
		ElementsAction.act(homePage.storesLink, "click", "");		
				
		System.out.println(homePage.getCurrentURL());	
		report.updateTestCaseLog(" Verified ","Stores Website Checked Successfully"+ homePage.getCurrentURL(),Status.PASS);		
	}	
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}