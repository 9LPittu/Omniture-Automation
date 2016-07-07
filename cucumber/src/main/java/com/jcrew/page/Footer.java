package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Footer {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Footer.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String footerItems[] = {"Let Us Help You", "Our Cards", "Our Stores","About J.Crew", "Get To Know Us"};
    
    @FindBy(className = "js-footer__row__wrap--main")
    private WebElement footerWrapMain;
    
    @FindBy(className = "footer__row--bottom")
    private  WebElement footerRowBottom;
    
    @FindBy(className = "footer__row--top")
    private  WebElement footerRowTop;
    
    @FindBy(className = "js-footer__menu")
    private List<WebElement> subLinks;
    
    @FindBy(xpath="//legend[@class='footer__header footer__header__copy' and text()='like being first?']/following-sibling::input[@name='subscribeEmail']")
    private WebElement emailField;
      
    @FindBy(className="footer__signup__button")
    private WebElement footerSignUpButton;
    
    @FindBy(className="js-footer__signup__copy")
    private WebElement footerSignUpMessage;
    
    @FindBy(className="footer__country-context")
    private WebElement shipToSectionInFooter;
    
    @FindBy(xpath=".//div[@class='footer__country-context']/descendant::a[@class='footer__country-context__link']")
    private WebElement changeLinkInFooter;
    
    @FindBy(className="footer__header--social")
    private WebElement socialSharingHeader;

    @FindBy(xpath = "//footer[@id='global__footer']")
    private WebElement footerSection;
    
    @FindBy(xpath="//a[contains(@class,'footer__fullsite__link')]")
    private WebElement viewFullSiteInFooter;
    
    public Footer(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(viewFullSiteInFooter));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(viewFullSiteInFooter));
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

    public boolean isAllFooterLinksPresent(){
        boolean result = true;
        for(String item:footerItems){
            result &= isFooterLinkPresent(item);
        }
        return result;
    }

    public boolean isFooterSectionDisplayed() {
        return footerSection.isDisplayed();
    }

    private WebElement getFooterLinkElement(String footerLink) {
        Util.waitWithStaleRetry(driver,footerWrapMain);
        try {
            return footerWrapMain.findElement(By.xpath("//h6[text()='" + footerLink + "']"));
        } catch (StaleElementReferenceException e) {
            logger.debug("Stale Element Exception was thrown, retry to click on footer element {}", footerLink);
            return getFooterLinkElement(footerLink);
        }
    }

    public void click_to_open_drawer(String footerLink) {

        Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();

        if (!(footerLink.equalsIgnoreCase("Our Cards") && !countryCode.equalsIgnoreCase("us"))) {
            WebElement fLink = getFooterLinkElement(footerLink);
            WebElement fLinkParent = fLink.findElement(By.xpath(".//parent::div"));
            String parentClass = fLinkParent.getAttribute("class");

            if (!parentClass.contains("is-expanded")) {
                fLink.click();
            }
        }

    }

    public void click_to_close_drawer(String footerLink) {
        WebElement fLink = getFooterLinkElement(footerLink);
        WebElement fLinkParent = fLink.findElement(By.xpath(".//parent::div"));
        String parentClass = fLinkParent.getAttribute("class");

        if (parentClass.contains("is-expanded"))
            fLink.click();
    }


    public String getFooterSubText(String footerLink) {
        WebElement listOfSubElements = getListOfSubElementsForFooterLink(footerLink);
        WebElement footerSubTextElement = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(listOfSubElements.findElement(By.className("footer__item__text"))));
        return footerSubTextElement.getText();
    }

    private WebElement getListOfSubElementsForFooterLink(String footerLink) {
        WebElement footerLinkElement = getFooterLinkElement(footerLink);
        return footerLinkElement.findElement(By.xpath("following-sibling::ul"));
    }

    public void click_sublink_from(String footerSubLink, String footerLink) {
    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String browser = propertyReader.getProperty("browser");
    	
    	if(footerSubLink.equals("J.Crew Factory") && browser.equalsIgnoreCase("phantomjs")){
    		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
    		logger.debug("# of tabs opened before j.crew factory link is clicked: {}", tabs.size());
            
            for(int i=1;i<tabs.size();i++){
            	driver.switchTo().window(tabs.get(i));
            	String tabTitle = driver.getTitle();
            	String tabUrl = driver.getCurrentUrl();
            	driver.close();
            	logger.debug("Tab is closed with page title as '{}'", tabTitle);
            	logger.debug("Tab is closed with page url as '{}'", tabUrl);
            }
            
            driver.switchTo().window(tabs.get(0));
    	}
    	
        WebElement listOfSubElements = getListOfSubElementsForFooterLink(footerLink);
        WebElement footerSublink = listOfSubElements.findElement(By.linkText(footerSubLink));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(footerSublink));
        logger.info("sub link being clicked {}", footerSubLink.toString());
        footerSublink.click();
    }

    public boolean isSubLinkDisplayed(String sublink) {
        
    	boolean subLinkDisplayed = false;  
    	
    	Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();       
        
        for(WebElement subLink: subLinks) {
            subLinkDisplayed = subLink.findElement(By.xpath("//a[text()='" + sublink + "']")).isDisplayed();
        }
        
        if(!subLinkDisplayed){
        	if(sublink.equalsIgnoreCase("Request A Style Guide") && !countryCode.equalsIgnoreCase("us") && !countryCode.equalsIgnoreCase("ca")){
        		subLinkDisplayed = true;
        	}
        }

        return subLinkDisplayed;
    }


    public String getFooterHeaderLegend() {
    	try{
    		return footerWrapMain.findElement(By.tagName("legend")).getText();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "";
    	}
    }

    public boolean isTopHeaderVisible(String text) {
        Util.waitWithStaleRetry(driver,footerRowTop);
        Util.waitForPageFullyLoaded(driver);
        WebElement footer = footerRowTop.findElement(By.xpath("//h6[text()='" + text + "']"));
        Util.waitWithStaleRetry(driver,footer);
        return footer.isDisplayed();
    }

    public boolean isIconAndTextDisplayed(String icon) {
    	
        WebElement contactItem;
        boolean isIconValidationRequired = true;
        boolean isIconDisplayed = true;
        
        try{
        	WebElement module = driver.findElement(By.className("footer__help__menu"));
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
        }
        catch(Exception e){
        	isIconDisplayed = false;
        	e.printStackTrace();
        }
                
        return isIconDisplayed;
    }

    public boolean isSocialIconDisplayed(String socialIcon) {
    	boolean iconDisplayed = false;
    	
    	try{
    		List<WebElement> socialNetworkIconsList = footerWrapMain.findElements(By.className("footer__social__menu"));
            for (WebElement socialNetworkIcon : socialNetworkIconsList) {
                iconDisplayed = socialNetworkIcon.findElement(By.className("footer-" + socialIcon)).isDisplayed();
            }
    	}
    	catch(Exception e){
    		iconDisplayed = false;
    		e.printStackTrace();
    	}
        
        return iconDisplayed;
    }

    public boolean isEmailFieldDisplayed()  {
    	try{
    		return footerWrapMain.findElement(By.tagName("input")).isDisplayed(); 
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }


    public void click_bottom_link(String bottomLink) {
        footerRowBottom.findElement(By.linkText(bottomLink)).click();
    }
    
    public boolean isEmailDisplayedUnderLikeBeingFirstSection(){    	
    	return emailField.isDisplayed();
    }
    
    public boolean isEmailFieldMatchesWithDefaultText(String defaultText){
    	String emailFieldDefaultText = emailField.getAttribute("placeholder").trim();
    	return emailFieldDefaultText.equalsIgnoreCase(defaultText);
    }
    
    public void enterEmailAddressInFooterEmailField(String emailAddress){
    	emailField.clear();
    	emailField.sendKeys(emailAddress);
    }
    
    public void clickSignUpButtonInFooter(){
    	footerSignUpButton.click();
    }
    
    public boolean isMessageDisplayedCorrectlyDuringFooterSignUp(String message) throws InterruptedException{    	

    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(footerSignUpMessage));
        Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(footerSignUpMessage, message.replaceAll("THANK YOU...", "")));
		String actualMessage = footerSignUpMessage.getText().toLowerCase();
		actualMessage = actualMessage.replace("\n", "");
		System.out.println("Message displayed:" + actualMessage);
		return actualMessage.contains(message.toLowerCase());
    }
    
    public boolean isShipToSectionDisplayed(){
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shipToSectionInFooter));
    	return shipToSectionInFooter.isDisplayed();
    }
    
    public boolean isCountryNameDisplayedInFooter(){
        return getCountryNameFooterElement().isDisplayed();
    }
    
    public boolean isChangeLinkDisplayedInFooter(){
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(changeLinkInFooter));
    	return changeLinkInFooter.isDisplayed();
    }

    public void clickChangeLinkInFooter(){    	
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shipToSectionInFooter));
    	String url = driver.getCurrentUrl();
    	
    	int i = 0;
    	while(i<=2){
    		try{
    			Util.createWebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(changeLinkInFooter));
    	    	Util.createWebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(changeLinkInFooter));
    	    	changeLinkInFooter.click();
    	    	Util.createWebDriverWait(driver,20).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    	    	Util.waitLoadingBar(driver);
    	    	break;
    		}
    		catch(Exception e){
    			logger.info("Change link element is not displayed...");
    			i++;
    			if(i>2){
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    public void selectCountry(String country){
    	List<WebElement> countryNames = driver.findElements(By.className("submitCountryChange"));
    	for(int i=0;i<countryNames.size();i++){
    		WebElement countryName = countryNames.get(i);
    		if(countryName.getText().equalsIgnoreCase(country)){
    			countryName.click();
    			break;
    		}
    	}
    }
    
    public boolean isChangedCountryNameDsiplayedInFooter(String country){

    	Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(getCountryNameFooterElement(), country));
    	String currentCountryName = getCountryNameFooterElement().getText().trim();
    	return currentCountryName.equalsIgnoreCase(country);
    }
    
    public boolean isSocialSharingSectionHeaderNameDisplayedCorrectly(String headerName){
    	return socialSharingHeader.getText().trim().equalsIgnoreCase(headerName);
    }
    
    public void clickSocialSharingIcon(String socialSharingIconName){
    	WebElement socialSharingIcon = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='footer__social__menu']/" +
                "descendant::li/a/i[contains(@class,'icon-social-" + socialSharingIconName.toLowerCase() + "')]")));
    	socialSharingIcon.click();
    }
    
    public boolean isViewFullSiteDisplayedAfterLegalLinks(){
    	WebElement viewFullSite  = driver.findElement(By.xpath("//nav[@class='c-footer__copyright']/" +
                "following-sibling::div[contains(@class,'c-footer__fullsite')]"));
    	return viewFullSite.isDisplayed();
    }
    
    public void clickViewFullSite(){
    	WebElement viewFullSiteLink  = driver.findElement(
                By.xpath("//a[@class='footer__fullsite__link js-footer__fullsite__link']"));
    	viewFullSiteLink.click();
    }
    
    public boolean isLegalLinksSectionDisplayed(){
    	WebElement legalLinksSection = driver.findElement(By.className("footer__copyright__menu"));
    	return legalLinksSection.isDisplayed();
    }
    
    public boolean isLinkDisplayedInLegalLinksSection(String expectedLinkName){
    	
    	boolean blnFlag = false;
    	
    	List<WebElement> legalSectionLinks = driver.findElements(By.className("footer__copyright__item"));
    	
    	for(WebElement legalSectionLink:legalSectionLinks){
    		String legalSectionLinkName = legalSectionLink.getText().trim();
    		if(legalSectionLinkName.toLowerCase().contains(expectedLinkName.toLowerCase())){
    			blnFlag = true;
    			break;
    		}
    	}
    	
    	return blnFlag;
    }
    
    public void clickLinkInLegalLinksSection(String linkName){
        Util.waitLoadingBar(driver);
    	List<WebElement> legalSectionLinks = driver.findElements(By.className("footer__copyright__link"));
    	
    	for(WebElement legalSectionLink:legalSectionLinks){
    		String legalSectionLinkName = legalSectionLink.getText().trim();
    		if(legalSectionLinkName.equalsIgnoreCase(linkName)){
    			legalSectionLink.click();
    			break;
    		}
    	}
    }
    
    public boolean isLinkNotDisplayedInLegalLinksSection(String text){    	
    	WebElement element = driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));    	
    	String tagName = element.getTagName();
    	
    	return !tagName.equalsIgnoreCase("a");
    }
    
    public boolean isContentGroupingDisplayedInCollapsed(String contentGroupingName){
    	
    	boolean isContentGroupingValidationRequired = true;
    	boolean isContentGroupingCollapsed = true;
    	
    	Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();
    	
    	if(contentGroupingName.equalsIgnoreCase("Our cards") && !countryCode.equalsIgnoreCase("us")){
    		isContentGroupingValidationRequired = false;
    	}
    	
    	WebElement element = null;
    	if(isContentGroupingValidationRequired){
    		try{
    			element = driver.findElement(By.xpath("//h6[contains(text(),'" + contentGroupingName + "')]/descendant::i[contains(@class,'icon-see-more')]"));
    			isContentGroupingCollapsed = element.isDisplayed();
    		}
    		catch(Exception e){
    			isContentGroupingCollapsed = false;
    		}
    	}
    	
    	return isContentGroupingCollapsed;
    }
    
    public void clickContentGrouping(String contentGrouping){
    	WebElement element = driver.findElement(By.xpath("//h6[contains(text(),'" + contentGrouping + "')]"));
    	element.click();
    }
    
    public boolean isContentGroupingDrawerOpened(String contentGroupingName){
    	
    	Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();
        
    	boolean isDrawerOpenedValidationRequired = true;
    	boolean isDrawerOpened = true;
    	
    	if(!countryCode.equalsIgnoreCase("us") && contentGroupingName.equalsIgnoreCase("Our cards")){
    		isDrawerOpenedValidationRequired = false;
    	}
    	
    	if(isDrawerOpenedValidationRequired){
    		WebElement element = driver.findElement(By.xpath("//h6[contains(text(),'" + contentGroupingName + "')]/parent::div[contains(@class,'is-expanded')]"));
    		isDrawerOpened = element.isDisplayed();
    	}
    	
    	return isDrawerOpened;
    }
    
    public boolean isContentGroupingDrawerClosed(String contentGroupingName){
    	
    	Country c = (Country) stateHolder.get("context");
        String countryCode = c.getCountry();
        
    	boolean isDrawerClosed = true;
    	
    	if(countryCode.equalsIgnoreCase("us")){
    		WebElement drawer = driver.findElement(By.xpath("//h6[contains(text(),'" + contentGroupingName + "')]/parent::div"));
            String drawerClass = drawer.getAttribute("class");
            isDrawerClosed = !drawerClass.contains("is-expanded");
    	}

        return isDrawerClosed;
    }

    public String isCorrectCountryNameDisplayedInFooter() {
    	String actualCountryName;
    	try{
    		WebElement countryNameInFooter = getCountryNameFooterElement();
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(countryNameInFooter));
    		actualCountryName = countryNameInFooter.getText();
    		logger.info("Country selected: {}", actualCountryName);
    	}
    	catch(Exception e){
    		actualCountryName = "";
    		Util.logBrowserErrorMessages(driver);
    	}

        return actualCountryName;
    }

    public WebElement getCountryNameFooterElement() {
    	int i = 0;
    	WebElement countryNameElement = null;
    	while(i<=2 && countryNameElement == null){
    		try{
    			Util.createWebDriverWait(driver,20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer[@id='global__footer']")));
    			countryNameElement = Util.createWebDriverWait(driver,20).until(ExpectedConditions.visibilityOfElementLocated(
        		                        By.xpath("//div[@class='footer__country-context']/descendant::span[@class='footer__country-context__country']")));
    			break;
    		}
    		catch(Exception e){
    			logger.info("Country element is not displayed...");
    			i++;
    			if(i>2){
    				e.printStackTrace();
    			}
    		}
    	}
        
        return countryNameElement;
    }
}