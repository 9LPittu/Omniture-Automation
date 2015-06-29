package testscripts.scenario1;
import java.io.IOException;

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
public class JCrewGetToKnowUs extends TestCase
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
		JCrewHomePage homePage = new JCrewHomePage();		
		ElementsAction.cssSelector("i.icon-footer.icon-social-facebook", "click", "");
		//ElementsAction.act(homePage.socialFacebook,"click","");			
		ElementsAction.callMeToWait(1000);
		report.updateTestCaseLog("Verified","Facebook Site:" +homePage.driver.getCurrentUrl(),Status.PASS);
		homePage.driver.navigate().back();
		ElementsAction.callMeToWait(1000);
		
		//Social - Twitter
		ElementsAction.act(homePage.socialTwitter,"click","");
		ElementsAction.callMeToWait(1000);		
		report.updateTestCaseLog("Verified","Twitter Site:" +homePage.driver.getCurrentUrl(),Status.PASS);
		homePage.driver.navigate().back();
		ElementsAction.callMeToWait(1000);
		
		//Social - Tumblr
		ElementsAction.act(homePage.socialTumblr,"click","");
		ElementsAction.callMeToWait(1000);
		report.updateTestCaseLog("Verified","Tumbler Site:" +homePage.driver.getCurrentUrl(),Status.PASS);
		homePage.driver.navigate().back();
		ElementsAction.callMeToWait(1000);
		
		//Social - Pinterest
		ElementsAction.act(homePage.socialPinterest,"click","");		
		ElementsAction.callMeToWait(1000);		
		report.updateTestCaseLog("Verified","Pinterest Site:" +homePage.driver.getCurrentUrl(),Status.PASS);
		homePage.driver.navigate().back();
		ElementsAction.callMeToWait(1000);
		
		//Social - Instagram
		ElementsAction.act(homePage.socialInstagram,"click","");
		ElementsAction.callMeToWait(1000);
		report.updateTestCaseLog("Verified","Instagram Site:" +homePage.driver.getCurrentUrl(),Status.PASS);
		homePage.driver.navigate().back();
		ElementsAction.callMeToWait(1000);
		
		//Social - Google
		ElementsAction.act(homePage.socialGoogle,"click","");
		ElementsAction.callMeToWait(1000);	
		report.updateTestCaseLog("Verified","Google Site:" +homePage.driver.getCurrentUrl(),Status.PASS);
		homePage.driver.navigate().back();
		ElementsAction.callMeToWait(1000);
		
		//Social - Youtube
		ElementsAction.act(homePage.socialYoutube,"click","");
		ElementsAction.callMeToWait(1000);
		report.updateTestCaseLog("Verified","YouTube Site:" +homePage.driver.getCurrentUrl(),Status.PASS);
		homePage.driver.navigate().back();
		ElementsAction.callMeToWait(1000);		
	}	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
	
	
	
	
}