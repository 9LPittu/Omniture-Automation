package com.jcrew.steps;

import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenericVerificationSteps extends DriverFactory{
    
	@And("Verify sale landing url is ([^\"]*)$")
    public void verify_sale_landing_url(String url) {
	WebDriver driver = 	getDriver();
	String currentURL = driver.getCurrentUrl();
	assertTrue("URL should contain " + url, currentURL.contains(url));
	
	}

}