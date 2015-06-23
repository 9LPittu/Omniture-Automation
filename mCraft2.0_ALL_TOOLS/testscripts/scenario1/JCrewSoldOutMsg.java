package testscripts.scenario1;
import java.io.IOException;


import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.cognizant.framework.IterationOptions;
import com.cognizant.framework.Status;

import supportlibraries.DriverScript;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;
 



/**
 * Test for login with valid user credentials
 * @author Cognizant
 */
public class JCrewSoldOutMsg extends TestCase
{
	
	
	@Test()
	public void runTC1() throws IOException
	{ 
		// Modify the test parameters as required
		
	    	
	     
	    testParameters.setCurrentTestDescription("Test for login with valid user credentials");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		
		//testParameters.setBrowser(MobilePlatform.Web);
		
		driverScript = new DriverScript(testParameters);
		driverScript.setLinkScreenshotsToTestLog(true);
		driverScript.driveTestExecution(); 
		
		
	}
	
	@Override
	public void setUp()
	{	
		ElementsAction.setDriver(report);
		report.updateTestCaseLog("Invoke Application", "Invoke the application under test  ", Status.DONE);
	}
	
	@Override
	public void executeTest()
	{
		//Home Page Objects - Hamburger, Department and Category
		JCrewHomePage homePage = new JCrewHomePage(webdriver);		
		
		homePage.hamburgerMenu.click();
		report.updateTestCaseLog(" Verified ","Hamburger Menu Clicked Successfully",Status.PASS);
				
		String deptName = dataTable.getData("General_Data","DepartmentName");
		homePage.getDepartmentByText(deptName,report).click();
		report.updateTestCaseLog(" Verified","Department clicked Successfully",Status.PASS);
		
		homePage.getCategoryByText(dataTable.getData("General_Data","CategoryName"),report).click();
		report.updateTestCaseLog(" Verified","Category Clicked Successfully",Status.PASS);

		
		//Sub-Category Page - Sub Category and Product Selection
		this.sleep(4);
		JCrewSubCategoryPage jcrewSubCategoryPage = new JCrewSubCategoryPage(webdriver);
		
		
		if(jcrewSubCategoryPage.getSubCategoryHeaderString().length() > 0 )
			report.updateTestCaseLog(" Verified", "Sub Category product header found is " +  jcrewSubCategoryPage.getSubCategoryHeaderString() ,Status.PASS); 
				
		WebElement elementToClick = null; 
		try {
			int subCategory = Integer.parseInt(dataTable.getData("General_Data","Sub-Category"));
			int productName = Integer.parseInt(dataTable.getData("General_Data","Product"));
			elementToClick = jcrewSubCategoryPage.getProductToSelect(subCategory,productName);
			elementToClick.click();
			} catch (Exception e) {
			report.updateTestCaseLog(" Failed" , "Single click  over the product " ,Status.FAIL);			
			e.printStackTrace();						
		}		
		if(elementToClick!=null)
			report.updateTestCaseLog(" Verified" , "Single click  over the product  " ,Status.PASS);
		
		
		//PDP Page Elements - Size, Add to Bag and Checkout
		this.sleep(4);
		JCrewProductDetailPage jcrewPDP = new JCrewProductDetailPage(webdriver);
		jcrewPDP.getFirstSize().click();
		report.updateTestCaseLog(" Verified" , "Size Clicked Successfully  " ,Status.PASS);		
		
		jcrewPDP.setQuantity("9", report);
		report.updateTestCaseLog(" Verified" , "Quantity Clicked Successfully  " ,Status.PASS);
		
		jcrewPDP.getAddToBagBtn().click();		
		report.updateTestCaseLog(" Verified" , "Added Item to Bag Successfully" ,Status.PASS);
		
		jcrewPDP.getFirstSize().click();
		report.updateTestCaseLog(" Verified" , "Size Clicked Successfully  " ,Status.PASS);		
		
		jcrewPDP.setQuantity("9", report);
		report.updateTestCaseLog(" Verified" , "Quantity Clicked Successfully  " ,Status.PASS);		
		
		jcrewPDP.getAddToBagBtn().click();		
		report.updateTestCaseLog(" Verified" , "Added Item to Bag Successfully" ,Status.PASS);
		
		if(jcrewPDP.getSoldOutMsg().length() > 0 )
			report.updateTestCaseLog(" Verified", "Sold Out Message is " +  jcrewPDP.getSoldOutMsg() ,Status.PASS);
		
		jcrewPDP.getCheckout().click();
		report.updateTestCaseLog(" Verified" , "Checkout Successfully" ,Status.PASS);
		
		this.sleep(4);
	}
	
	public void sleep(int seconds) 
	{
	    try {
	        Thread.sleep(seconds * 1000);
	    } catch (InterruptedException e) {

	    }
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}