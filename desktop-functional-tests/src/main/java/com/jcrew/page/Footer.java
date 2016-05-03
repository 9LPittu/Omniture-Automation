package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nadiapaolagarcia on 4/19/16.
 */
public class Footer {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Footer.class);
    private final WebDriverWait wait;
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy(id = "global__footer")
    private WebElement global__footer;

    @FindBy(className="footer__country-context")
    private WebElement shipToSectionInFooter;

    @FindBy(xpath=".//div[@class='footer__country-context']/descendant::span[@class='footer__country-context__country']")
    private WebElement countryNameInFooter;

    @FindBy(xpath=".//div[@class='footer__country-context']/descendant::a[@class='footer__country-context__link']")
    private WebElement changeLinkInFooter;
    
    @FindBy(className = "footer__row--top")
    private  WebElement footerRowTop;
    
    @FindBy(className = "js-footer__row__wrap--main")
    private WebElement footerWrapMain;

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(global__footer));
    }

    public String getCountry() {
        WebElement country = global__footer.findElement(By.className("footer__country-context__country"));
        String countryName = country.getText();

        return  countryName;
    }

    public boolean isShipToSectionDisplayed(){
        return shipToSectionInFooter.isDisplayed();
    }

    public boolean isCountryNameDisplayedInFooter(){
        return countryNameInFooter.isDisplayed()&& countryNameInFooter.getText().equals("United States");
    }

    public boolean isChangeLinkDisplayedInFooter(){
        return changeLinkInFooter.isDisplayed();
    }

    public void clickChangeLinkInFooter(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(changeLinkInFooter));
        changeLinkInFooter.click();
    }

    public boolean isCorrectCountryNameDisplayedInFooter(){

        Country c = (Country)stateHolder.get("context");
        String expectedCountryName = c.getName();
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(countryNameInFooter));
        String actualCountryName = countryNameInFooter.getText();

        logger.info("Expected country to be selected: {}", expectedCountryName);
        logger.info("Actual country selected: {}", actualCountryName);

        return actualCountryName.equalsIgnoreCase(expectedCountryName);
    }

    public boolean isTopHeaderVisible(String text) {
        Util.waitWithStaleRetry(driver,footerRowTop);
        Util.waitForPageFullyLoaded(driver);
        WebElement footer = footerRowTop.findElement(By.xpath("//h6[text()='" + text + "']"));
        Util.waitWithStaleRetry(driver,footer);
        return footer.isDisplayed();
    }
    
    public boolean isIconAndTextDisplayed(String icon) {
    	
        WebElement module = driver.findElement(By.className("footer__help__menu"));
        WebElement contactItem;
        boolean isIconValidationRequired = true;
        boolean isIconDisplayed = true;
        
    	Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();
        
        if(icon.equalsIgnoreCase("vps") && (countryCode.equalsIgnoreCase("au") || countryCode.equalsIgnoreCase("sg") || countryCode.equalsIgnoreCase("hk") || countryCode.equalsIgnoreCase("de") || countryCode.equalsIgnoreCase("jp") || countryCode.equalsIgnoreCase("ch"))){
        	isIconValidationRequired = false;
        }
        
        if(icon.equalsIgnoreCase("phone") && countryCode.equalsIgnoreCase("jp")){
        	isIconValidationRequired = false;
        }

        if(isIconValidationRequired){        	
        	switch (icon){
	            case "twitter":
	                contactItem = module.findElement(By.className("footer__help__item--twitter"));
	                break;
	            case "phone":
	                contactItem = module.findElement(By.className("footer__help__item--phone"));
	                break;
	            case "vps":
	                contactItem = module.findElement(By.className("footer__help__item--vps"));
	                break;
	            default:
	                logger.debug("icon {} not found",icon);
	                return false;
        	}

        	isIconDisplayed = contactItem.findElement(By.tagName("i")).isDisplayed();
        }
        
        return isIconDisplayed;

    }
    
public boolean isFooterLinkPresent(String footerLink) {
    	
    	Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();
        
    	boolean isFooterLinkDisplayed = false;
    	
    	try{
    		isFooterLinkDisplayed = getFooterLinkElement(footerLink).isDisplayed();
    	}
    	catch(Exception e){
    		isFooterLinkDisplayed = false;
    	}
    	
    	if(!isFooterLinkDisplayed){
    		if(footerLink.equalsIgnoreCase("Our Cards") && !countryCode.equalsIgnoreCase("us")){
    			isFooterLinkDisplayed = true;
    		}
    	}
    	else if(footerLink.equalsIgnoreCase("Our Cards") && countryCode.equalsIgnoreCase("ca")){
    			isFooterLinkDisplayed = false;
    	}
    	
        return isFooterLinkDisplayed;
    }

	private WebElement getFooterLinkElement(String footerLink) {
		Util.waitWithStaleRetry(driver,footerWrapMain);
		try {
			return footerWrapMain.findElement(By.xpath("//h6[text()='" + footerLink + "']"));
		}
		catch (StaleElementReferenceException e) {
			logger.debug("Stale Element Exception was thrown, retry to click on footer element {}", footerLink);
			return getFooterLinkElement(footerLink);
		  }
	}
	
	public String getFooterHeaderLegend() {
        return footerWrapMain.findElement(By.tagName("legend")).getText();
    }
	
	public boolean isEmailFieldDisplayed()  {
        return footerWrapMain.findElement(By.tagName("input")).isDisplayed();
    }
	
	public boolean isSocialIconDisplayed(String socialIcon) {
        List<WebElement> socialNetworkIconsList = footerWrapMain.findElements(By.className("footer__social__menu"));
        boolean iconDisplayed = false;
        for(WebElement socialNetworkIcon: socialNetworkIconsList) {
            //Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(contactUsIcon.findElement(By.cssSelector("a[href*='"+icon+"']"))));
            iconDisplayed =socialNetworkIcon.findElement(By.className("footer-"+socialIcon)).isDisplayed();
        }
        return iconDisplayed;
    }
}