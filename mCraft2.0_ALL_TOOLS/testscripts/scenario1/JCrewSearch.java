package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 */
public class JCrewSearch extends TestCase
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
		
		ElementsAction.act(homePage.menuSearch, "click", "");
		
		ElementsAction.act(homePage.searchClose, "click", "");
						
		ElementsAction.act(homePage.menuSearch, "click", "");
		
		ElementsAction.callMeToWait(1000);
		
		String searchStr = dataTable.getData("General_Data","SearchKeyword");
		ElementsAction.act(homePage.searchTextBox, "entertext", searchStr);
				
		ElementsAction.act(homePage.searchIcon, "click", "");
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}