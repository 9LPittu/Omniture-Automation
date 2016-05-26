package com.jcrew.page;

import com.jcrew.util.Util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class JcrewFactoryPage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(JcrewFactoryPage.class);

    public JcrewFactoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Boolean isJcrewFactoryPage() {
        
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        logger.debug("# of tabs opened when j.crew factory link is clicked: {}", tabs.size());
        
        boolean isJcrewFactoryPageDisplayed = false;        
        for(int i=1;i<tabs.size();i++){
        	driver.switchTo().window(tabs.get(i));
        	logger.debug("Current tab URL: {}", driver.getCurrentUrl());
        	logger.debug("Current tab title: {}", driver.getTitle());
        	
        	try{
        		isJcrewFactoryPageDisplayed = Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("factory.jcrew.com"));
        		if(isJcrewFactoryPageDisplayed){        		
            		break;
            	}
        	}
        	catch(Exception e){
        		logger.debug("factory.jcrew.com is not displayed in tab {}", i);
        	}
        }
     
        return isJcrewFactoryPageDisplayed;
    }
}