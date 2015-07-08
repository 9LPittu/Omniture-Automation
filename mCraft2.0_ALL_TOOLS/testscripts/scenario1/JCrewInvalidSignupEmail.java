package testscripts.scenario1;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;


/**
 * @author Cognizant
 */
public class JCrewInvalidSignupEmail extends TestCase
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
		
		ElementsAction.callMeToWait(10);	
						
		ElementsAction.act(homePage.emailFail, "verifytext", "Please enter a valid email address.");			
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}