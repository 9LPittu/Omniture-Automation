package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import functionallibraries.JCrewProductDetailPage;
import functionallibraries.JCrewSubCategoryPage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 * Test Case for Final Sale - Rally User Story - US7728
 */
public class JCrewIsFinalSale extends TestCase
{	
	@Override
	public void setUp() {
		//Nothing to do	
	}
	
	@Override
	public void executeTest()
	{		
		JCrewHomePage homePage = new JCrewHomePage();		
		ElementsAction.act(homePage.hamburgerMenu,"click","");
				
		homePage.deptClick(dataTable.getData("General_Data","DepartmentName"));
			
		homePage.categoryClick(dataTable.getData("General_Data","CategoryName"));
		
		ElementsAction.callMeToWait(1000);
			
		JCrewSubCategoryPage jcrewSubCategoryPage = new JCrewSubCategoryPage();
		int subCategory = Integer.parseInt(dataTable.getData("General_Data","Sub-Category"));
		int productName = Integer.parseInt(dataTable.getData("General_Data","Product"));
		ElementsAction.act(jcrewSubCategoryPage.getProductToSelect(subCategory,productName), "click", "");
		
		JCrewProductDetailPage jcrewPDP = new JCrewProductDetailPage();
		
		ElementsAction.act(jcrewPDP.size,"click","");
		
		if(jcrewPDP.isFinalSale.getText().substring(0,11).equalsIgnoreCase("FINAL SALE:"))
			report.updateTestCaseLog("Verified ", "Is Final Sale", Status.PASS);
		else		
			report.updateTestCaseLog("Not Verified ", "Is Final Sale", Status.FAIL);
				
		ElementsAction.act(jcrewPDP.addToBagBtn,"click","");
		
		ElementsAction.act(jcrewPDP.checkOut,"click","");		
	}
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
}