package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import functionallibraries.JCrewProductDetailPage;
import functionallibraries.JCrewSubCategoryPage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

/**
 * @author Cognizant
 * Test Case for VPS - Rally User Story - US10450
 */
public class JCrewVPS extends TestCase
{
	@Override
	public void setUp() {
		ElementsAction.setDriver(report);
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
		String strVPS = "Since this is a special, limited-edition item with a small quantity available, our Very Personal Stylists are on hand to help you purchase yours.\nCall 800 261 7422 or email erica@jcrew.com to order.";		
		if(jcrewPDP.vpsMessage.getText().equalsIgnoreCase(strVPS))
		{
			report.updateTestCaseLog("Verified", strVPS.replace("\n", ""), Status.PASS);
		}						
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}