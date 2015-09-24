package com.jcrew.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;

public class Reporting {  
    
    public void takeScreenshot(Scenario scenario){ 	
		
	      try {       	
	      	final PropertyReader propertyReader = PropertyReader.getPropertyReader();        	
	          WebDriver driver = new DriverFactory().getDriver();
	      	if(propertyReader.getScreenshotForEveryStep().equalsIgnoreCase("yes")){
	      		byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);        		
	      		scenario.embed(screenshot, "image/png");
	      	}
	      }
	    
	      catch (Exception e) {
	          e.printStackTrace();  
	      }     
    }
}