package com.jcrew.steps;

import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class GenericVerificationSteps extends DriverFactory{
    
	@And("Verify url is ([^\"]*)$")
    public void verify_sale_landing_url(String url) {
		WebDriver driver = 	getDriver();
		String currentURL = driver.getCurrentUrl();
		assertTrue("URL should contain " + url + " . Current URL is " + currentURL , currentURL.contains(url));
	
	}

}