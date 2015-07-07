package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import functionallibraries.JCrewProductDetailPage;
import functionallibraries.JCrewSubCategoryPage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 */
public class JCrewBackordered extends TestCase
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
		ElementsAction.act(homePage.hamburgerMenu,"click","");
				
		homePage.deptClick(dataTable.getData("General_Data","DepartmentName"));
			
		homePage.categoryClick(dataTable.getData("General_Data","CategoryName"));
		
		ElementsAction.callMeToWait(2000);
			
		JCrewSubCategoryPage jcrewSubCategoryPage = new JCrewSubCategoryPage();
		int subCategory = Integer.parseInt(dataTable.getData("General_Data","Sub-Category"));
		int productName = Integer.parseInt(dataTable.getData("General_Data","Product"));
		ElementsAction.act(jcrewSubCategoryPage.getProductToSelect(subCategory,productName), "click", "");
		
		JCrewProductDetailPage jcrewPDP = new JCrewProductDetailPage();
		
		ElementsAction.act(jcrewPDP.size,"click","");
		
		if(jcrewPDP.backOrdered.getText().substring(0,12).equalsIgnoreCase("BackOrdered:"))
		{
			report.updateTestCaseLog("Verified ", "Backordered", Status.PASS);
		}
		else
		{
			report.updateTestCaseLog("Not Verified ", "Backordered", Status.FAIL);
		}
		
		ElementsAction.act(jcrewPDP.addToBagBtn,"click","");
		
		ElementsAction.act(jcrewPDP.checkOut,"click","");		
	}
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}