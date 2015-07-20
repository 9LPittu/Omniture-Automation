package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 * Test Case for Search - Rally User Story - US12474 & US10940
 * @param <WebElement>
 */
public class JCrewSearch<WebElement> extends TestCase
{
	@Override
	public void setUp() {
		ElementsAction.setDriver(report);	
	}
	
	@Override
	public void executeTest()
	{		
		JCrewHomePage homePage = new JCrewHomePage();
		
		ElementsAction.callMeToWait(1000);
		ElementsAction.act(homePage.menuSearch, "click", "");		
		
		String searchStr = dataTable.getData("General_Data","SearchKeyword");
		ElementsAction.act(homePage.searchTextBox, "entertext", searchStr+"\n");
		
		report.updateTestCaseLog("Results Verified", homePage.searchResults.getText(), Status.PASS);
		
		ElementsAction.callMeToWait(2000);
		int searchDept = Integer.parseInt(dataTable.getData("General_Data","SearchDept"));
		homePage.getSearchDeptList(searchDept);
		
		ElementsAction.callMeToWait(1000);
		ElementsAction.act(homePage.searchRefine,"click", "");
		ElementsAction.callMeToWait(1000);
		ElementsAction.act(homePage.refineList,"click", "");
		ElementsAction.callMeToWait(1000);
		ElementsAction.act(homePage.refineClose,"click", "");
		
		ElementsAction.callMeToWait(1000);
		report.updateTestCaseLog("Results Verified", homePage.searchResults.getText(), Status.PASS);
		
		if(searchDept==1)
			ElementsAction.act(homePage.womenDeselect, "click", "");
		else if(searchDept==2)
			ElementsAction.act(homePage.menDeselect, "click", "");
		else if(searchDept==3)
			ElementsAction.act(homePage.girlsDeselect, "click", "");
		else if(searchDept==4)
			ElementsAction.act(homePage.boysDeselect, "click", "");
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
}