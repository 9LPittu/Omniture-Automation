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
public class JCrewOutOfStock extends TestCase
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

		ElementsAction.callMeToWait(1000);
		JCrewSubCategoryPage jcrewSubCategoryPage = new JCrewSubCategoryPage();
		int subCategory = Integer.parseInt(dataTable.getData("General_Data","Sub-Category"));
		int productName = Integer.parseInt(dataTable.getData("General_Data","Product"));
		ElementsAction.act(jcrewSubCategoryPage.getProductToSelect(subCategory,productName), "click", "");
						
		JCrewProductDetailPage jcrewPDP = new JCrewProductDetailPage();
		ElementsAction.act(jcrewPDP.size,"click","");
		ElementsAction.act(jcrewPDP.outofStockText,"verifytext","OUT OF STOCK");		
	}			
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}	
	
}