package testscripts.scenario1;

import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;


/**
 * @author Cognizant
 * Test Case for Customers Also Love - Rally User Story - US8247
 */
public class JCrewValidSignupEmail extends TestCase
{
	@Override
	public void setUp() {
		//Nothing to do	
	}
	
	@Override
	public void executeTest()
	{		
		JCrewHomePage homePage = new JCrewHomePage();
		
		String emailText = dataTable.getData("General_Data","EmailID");		
		ElementsAction.act(homePage.emailTextBox, "entertext", emailText);
		
		ElementsAction.act(homePage.signupBtn, "click", "");				
		
		ElementsAction.callMeToWait(1000);
		
		String strValidMsg = "THANK YOU...\nYour email has been added to the crew.com email list. Stay tuned for news about special offers and more.";		
		if(homePage.emailSuccess.getText().equalsIgnoreCase(strValidMsg))
		{
			report.updateTestCaseLog("Verified", strValidMsg.replace("\n", ""), Status.PASS);
		}
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}