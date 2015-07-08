package testscripts.scenario1;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.cognizant.framework.Status;

import functionallibraries.JCrewHomePage;
import supportlibraries.ElementsAction;
import supportlibraries.TestCase;

 /**
 * @author Cognizant
 */
public class JCrewFooterLinks extends TestCase
{
	@Override
	public void setUp() {
		//Nothing to do	
	}
	
	@Override
	public void executeTest()
	{		
		JCrewHomePage homePage = new JCrewHomePage();			
		List<WebElement> linkElement = homePage.footerLinks;
		List<String>links = new ArrayList<String>();
		
		for(WebElement link:linkElement) 
		{
		  links.add(link.getAttribute("href"));
		}

		for(String link:links) 
		{
			homePage.driver.get(link);			
			report.updateTestCaseLog("Link ", link, Status.PASS);
		}
	}	
	
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}		
}