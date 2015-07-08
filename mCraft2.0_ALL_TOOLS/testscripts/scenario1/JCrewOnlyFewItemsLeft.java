package testscripts.scenario1;

import functionallibraries.JCrewHomePage;
import functionallibraries.JCrewProductDetailPage;
import functionallibraries.JCrewSubCategoryPage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;


/**
 * @author Cognizant
 * Test Case for Only Few Items Left - Rally User Story - US7564
 */
public class JCrewOnlyFewItemsLeft extends TestCase
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
		ElementsAction.act(jcrewPDP.onlyFewItemsLeft,"verifytext","IN STOCK – ONLY A FEW LEFT");					
		ElementsAction.act(jcrewPDP.addToBagBtn,"click","");		
		ElementsAction.act(jcrewPDP.checkOut,"click","");		
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
}