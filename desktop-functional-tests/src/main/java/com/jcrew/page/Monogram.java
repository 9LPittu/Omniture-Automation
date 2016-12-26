package com.jcrew.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.jcrew.utils.Util;

public class Monogram extends PageObject{

    @FindBy(className = "p-monogram--details")
    private WebElement monogramModalWindow;
    
    @FindBy(className="p-monogram-letter--section")
    private WebElement monogramLetterSection;

    public Monogram(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        
        wait.until(ExpectedConditions.visibilityOf(monogramModalWindow));        
    }
    
    public void selectPlacement(String placement){
    	
    	WebElement placementElement = null;
    	if(!placement.isEmpty()){
    	    placementElement = monogramModalWindow.findElement(By.xpath(".//div[contains(@class,'js-monogram__location')]/span["
    	    															+ Util.xpathGetTextLower + "='" +	placement.toLowerCase() + "']"));   	
    	}
    	else{
    		List<WebElement>  placementElements = monogramModalWindow.findElements(By.xpath(".//div[contains(@class,'js-monogram__location')]/span"));
    		placementElement = placementElements.get(Util.randomIndex(placementElements.size()));
    	}
    	
    	if(placementElement!=null){
    		placementElement.click();    	
    		logger.info("Selected placement: {}", placementElement.getText());
    	}
    	else{
    		String errorMsg = "Failed to identify placement in monogram window"; 
    		logger.error(errorMsg);
    		Util.e2eErrorMessagesBuilder(errorMsg);
    		throw new NullPointerException(errorMsg);
    	}
    }
    
    public void selectStyle(String style){
    	WebElement styleElement = null;
    	if(!style.isEmpty()){
    		styleElement = monogramModalWindow.findElement(By.xpath(".//div[contains(@class,'js-p-monogram--stamp')]/span[" 
    															    + Util.xpathGetTextLower + "='" +	style.toLowerCase() + "']"));   	
    	}
    	else{
    		List<WebElement>  styleElements = monogramModalWindow.findElements(By.xpath(".//div[contains(@class,'js-p-monogram--stamp')]/span"));
    		styleElement = styleElements.get(Util.randomIndex(styleElements.size()));
    	}
    	
    	if(styleElement!=null){
    		styleElement.click();    	
    		logger.info("Selected style: {}", styleElement.getText());
    	}
    	else{
    		String errorMsg = "Failed to identify style in monogram window"; 
    		logger.error(errorMsg);
    		Util.e2eErrorMessagesBuilder(errorMsg);
    		throw new NullPointerException(errorMsg);
    	}
    }
    
    public void selectInitial(String initialType, String initialVal){
    	if(initialVal.isEmpty())
    		return;
    	
    	WebElement initialElement = null;
    	
    	switch(initialType.toLowerCase()){
    		case "first":
    			initialElement =  monogramLetterSection.findElement(By.xpath(".//span[1]/select"));
    			break;
    		case "middle":
    			initialElement =  monogramLetterSection.findElement(By.xpath(".//span[2]/select"));
    			break;
    		case "last":
    			initialElement =  monogramLetterSection.findElement(By.xpath(".//span[3]/select"));
    			break;
    	}
    	
    	Select initialDropdown = new Select(initialElement);
    	initialDropdown.selectByVisibleText(initialVal);
    	logger.info("Selected {} initial: {}", initialType, initialDropdown.getFirstSelectedOption().getText());
    }
    
    public void selectThreadColor(String threadColor){
    	List<WebElement> colorsSection = monogramModalWindow.findElements(By.className("p-monogram__colors--section"));
    	
    	if(colorsSection.size()==0)
    		return;
    	
    	WebElement colorElement = null;
    	if(!threadColor.isEmpty()){
    		colorElement = colorsSection.get(0).findElement(By.xpath(".//li[contains(@class,'p-monogram__colors-list__item')]/span[text()='" + threadColor.toLowerCase() + "']"));
    	}
    	else{
    		List<WebElement> colorElements = colorsSection.get(0).findElements(By.xpath(".//li[contains(@class,'p-monogram__colors-list__item')]/span"));
    		colorElement = colorElements.get(Util.randomIndex(colorElements.size()));
    	}
    	
    	if(colorElement!=null){
    		colorElement.click();    	
    		logger.info("Selected color: {}", colorElement.getText());
    	}
    	else{
    		String errorMsg = "Failed to identify color in monogram window"; 
    		logger.error(errorMsg);
    		Util.e2eErrorMessagesBuilder(errorMsg);
    		throw new NullPointerException(errorMsg);
    	}
    }
    
    public void saveMonogram(){
    	WebElement saveMonogramElement = monogramModalWindow.findElement(By.className("p-monogram--save"));
    	Util.scrollAndClick(driver, saveMonogramElement);
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("p-monogram--save")));
    }
}