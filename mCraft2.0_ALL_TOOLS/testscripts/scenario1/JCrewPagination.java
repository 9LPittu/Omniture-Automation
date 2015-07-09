package testscripts.scenario1;

import functionallibraries.JCrewHomePage;
import functionallibraries.JCrewProductDetailPage;
import functionallibraries.JCrewSubCategoryPage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 * Test Case for Add to Bag - Rally User Story - US10609 
 */

public class JCrewPagination extends TestCase
{		  			
	@Override
	public void setUp() {
		ElementsAction.setDriver(report);
	}
	
	@Override
	public void executeTest()
	{			
       JCrewHomePage homePage=new JCrewHomePage();	       		
       ElementsAction.act(homePage.hamburgerMenu, "click", "");
	   homePage.deptClick(dataTable.getData("General_Data","DepartmentName"));			
	   homePage.categoryClick(dataTable.getData("General_Data","CategoryName"));						

	   ElementsAction.callMeToWait(1000);
		
	   JCrewSubCategoryPage jcrewSubCategoryPage = new JCrewSubCategoryPage();
	   //ElementsAction.act(jcrewSubCategoryPage.dropdownPagination, "select", "2");
	   ElementsAction.callMeToWait(10000);
	   ElementsAction.act(jcrewSubCategoryPage.dropdownPagination, "click", "");
	   ElementsAction.act(jcrewSubCategoryPage.pageLinkNext, "click", "");
	   ElementsAction.callMeToWait(10000);	   
	   //ElementsAction.act(jcrewSubCategoryPage.pageLinkPrev, "click", "");
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
}