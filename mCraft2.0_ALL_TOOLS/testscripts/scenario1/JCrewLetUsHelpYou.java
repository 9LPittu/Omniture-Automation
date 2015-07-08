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
public class JCrewLetUsHelpYou extends TestCase
{
	@Override
	public void setUp() {
		//Nothing to do	
	}
	
	@Override
	public void executeTest()
	{		
		JCrewHomePage homePage = new JCrewHomePage();
		
		List<WebElement> linkElement = homePage.helpLinks;
		List<String>links = new ArrayList<String>();
		
		for(WebElement link:linkElement) 
		{
		  links.add(link.getAttribute("href"));
		}
		String strLinks = "";
		for(String link:links) 
		{
			homePage.driver.get(link);			
			strLinks = strLinks + " " +link.toString();			
		}
		report.updateTestCaseLog("Link ", strLinks.trim(), Status.PASS);					
	}
		
	@Override
	public void tearDown()
	{
		// Nothing to do		
	}
}