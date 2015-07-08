package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 */
public class JCrewStores extends TestCase
{
	@Override
	public void setUp() {
		//Nothing to do	
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