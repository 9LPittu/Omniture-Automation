package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Header {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Header.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String menuItems[] = {"MENU", "SEARCH", "STORES","BAG"};

    @FindBy(className = "header__primary-nav__wrap")
    private WebElement headerWrap;
    @FindBy(className = "icon-close")
    private WebElement searchCloseIcon;
    @FindBy(id = "js-header__logo")
    private WebElement headerLogo;
    @FindBy(className = "c-header__breadcrumb")
    private WebElement breadcrumbSection;
    @FindBy(className = "js-primary-nav__link--search")
    private WebElement searchButton;
    @FindBy(className = "primary-nav__text--stores")
    private WebElement storesLink;
    @FindBy(id = "c-header__userpanel")
    private WebElement signInLink;
    @FindBy(id = "js-header__cart")
    private WebElement shoppingBagLink;
    @FindBy(css = ".icon-header.icon-header-bag.icon-bag")
    private WebElement bagIcon;
    @FindBy(id = "section1")
    private WebElement genderLandingSection;

    @FindBy(xpath="//li[@id='c-header__userpanel']/a/span[2]")
    private WebElement signInFromHeader;
    
    @FindBy(xpath="//div[@id='c-nav__userpanel']/span[@id='c-header__userpanelrecognized']")
    private WebElement myAccountFromHeader;
    
    @FindBy(xpath="//dl[@class='menu-show']/div[@class='userpanel__inner']")
    private WebElement myAccountDropdownOpened;
    
    @FindBy(xpath="//dl[@class='menu-hide']/div[@class='userpanel__inner']")
    private WebElement myAccountDropdownClosed;
    
    @FindBy(xpath=".//span[contains(@class,'userpanel__icon--close')]")
    private WebElement closeIconInMyAccountDropdown;
    
    @FindBy(xpath=".//dd[@class='c-nav__userpanel--welcomeuser']")
    private WebElement welcomeMessageInMyAccountDropdown;
    
    @FindBy(xpath=".//dd[contains(@class,'c-nav__userpanel-item')]/a[text()='My Details']")
    private WebElement myDetailsInMyAccountDropdown;
    
    @FindBy(xpath=".//dd[contains(@class,'c-nav__userpanel-item')]/a[text()='Sign Out']")
    private WebElement signOutInMyAccountDropdown;
    
    @FindBy(xpath=".//dd[@class='c-nav__userpanel-item--rewards']")
    private WebElement rewardsSectionInMyAccountDropdown;
    
    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isHeaderLinkPresent(String headerLink) {
    	logger.debug("Checking for header link: {}", headerLink);
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
    	
		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerWrap));
        WebElement headerLinkElement = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(headerWrap.findElement(
                		By.xpath("//span[contains(@class,'primary-nav__text') and " + Util.xpathGetTextLower + "='" + headerLink.toLowerCase() + "']"))));
        Util.createWebDriverWait(driver).until(
            ExpectedConditions.elementToBeClickable(headerLinkElement));
        return true;
    }

    public boolean isHeaderBagIconPresent() {
        try {
            return bagIcon.isDisplayed();
        } catch (StaleElementReferenceException sere) {
            return isHeaderBagIconPresent();
        }
    }

    public String getBagIconLinkText() {
        try {
            return driver.findElement(By.className("primary-nav__item--bag")).getText();
        } catch (StaleElementReferenceException sere) {
            return getBagIconLinkText();
        }
    }

    public boolean isSearchDrawerOpen() {
        WebElement headerSearchInput = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(headerWrap.findElement(By.className("header__search__input"))));
        return headerSearchInput.isDisplayed();
    }

    public boolean isSearchDrawerClosed() {
        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.invisibilityOfElementLocated(By.className("header__search__input")));
    }

    public void click_on_search_close_icon() {
        WebElement searchIconClose = headerWrap.findElement(By.className("js-header__search__button--close"));
        searchIconClose.click();
    }

    public String getSearchDrawerTerm() {
        return driver.getCurrentUrl();
    }

    public List<String> getPrimaryNavigationLinkNames() {
        List<String> primaryNavigationLinkNames = new ArrayList<>();

        List<WebElement> headerWrapElements = headerWrap.findElements(By.className("primary-nav__link"));

        for (WebElement headerElement : headerWrapElements) {
            primaryNavigationLinkNames.add(headerElement.getText());
        }

        return primaryNavigationLinkNames;
    }

    public boolean isJCrewLogoPresent() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerLogo));
        return headerLogo.isDisplayed();
    }



    public Point getLogoPosition() {
        return headerLogo.getLocation();
    }

    public void click_jcrew_logo() {
        Util.waitLoadingBar(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("js-footer__fullsite__link")));
        Util.clickWithStaleRetry(headerLogo);
    }

    public void click_on_search_button() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("js-footer__fullsite__link")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    public boolean isSearchLinkDisplayed() {
        return searchButton.isDisplayed();
    }

    public boolean isStoresLinkPresent() {
        return storesLink.isDisplayed();
    }

    public void click_on_stores_link() {
        storesLink.click();
    }

    public boolean isSignInPresent() {
        WebElement link =  Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='c-header__userpanel']/a")));
        return link.isDisplayed();
    }

    public boolean isMyAccountPresent() {
        WebElement link =  Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='c-nav__userpanel']/*[@id='c-header__userpanelrecognized']")));
        return link.isDisplayed();
    }

    public void click_on_sign_in() {
        WebElement link =  Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='c-header__userpanel']/a")));
        link.click();
    }

    public void click_item_bag() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(shoppingBagLink));
        shoppingBagLink.click();
    }

    public boolean isBagLinkDisplaying() {
        return shoppingBagLink.isDisplayed();
    }

    public void click_breadcrumb(String breadcrumb) {
        Util.waitWithStaleRetry(driver,breadcrumbSection);
        WebElement breadcrumbElement = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='" + breadcrumb + "' and @class='breadcrumb__link']")));
        breadcrumbElement.click();
    }

    public boolean isGenderLandingPage(String gender){
        Country country = (Country) stateHolder.get("context");
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(genderLandingSection));
        WebElement genderPageElement =genderLandingSection.findElement(By.xpath("//h2[contains(text(),'NEW FOR "+gender.toUpperCase()+"')]"));

        boolean isDisplayed = genderPageElement.isDisplayed();
        boolean isURL = Util.countryContextURLCompliance(driver,country);

        return isDisplayed & isURL;
    }

    public boolean isJcrewBreadCrumbNotDisplayed() {
        try {
            return !breadcrumbSection.isDisplayed();
        } catch (NoSuchElementException e) {
            logger.debug("bread crumb section not present");
            return true;
        }
    }

    public boolean isEmbeddedHeaderSectionDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerWrap));
        return headerWrap.isDisplayed();
    }

    public boolean isAllMenuItemsDisplayed(){
        boolean result = true;

        for(String item:menuItems){
            result &= isHeaderLinkPresent(item);
        }

        return result;
    }

    public String getBagButtonLink(){
        return shoppingBagLink.getAttribute("href");
    }

    public String getStoresButtonLink(){
        WebElement stores = driver.findElement(By.cssSelector(".primary-nav__item--stores > .primary-nav__link"));
        return stores.getAttribute("href");
    }
    
    public void clickElementFromHeader(String elementName){    	
    	WebElement headerElement = null;
    	switch(elementName.toUpperCase()){
    		case "SIGN IN":
    			headerElement = signInFromHeader;
    			break;
    		case "MY ACCOUNT":
    			headerElement = myAccountFromHeader;
    			break;
    	}
    	
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerElement));
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(headerElement));
    	headerElement.click();
    	logger.info("'{}' link is clicked from header...", elementName);
    	Util.waitLoadingBar(driver);
    }
    
    public boolean isMyAccountDropdownInExpectedState(String state){
    	boolean result = false;
    	if(state.equalsIgnoreCase("opened")){
    		Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(myAccountDropdownOpened));
    		result =  myAccountDropdownOpened.isDisplayed();    		
    	}
    	else if(state.equalsIgnoreCase("closed")){
    		Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(myAccountDropdownClosed)));
    		result = true;
    	}
    	
    	return result;
    }
    
    public boolean isAccountDropdownOptionsDisplayed(){
    	
    	boolean isWelcomeMessagePatternMatches = welcomeMessageInMyAccountDropdown.getText().matches("^Welcome, [A-Za-z]+(\\d+)?");
    	
    	return closeIconInMyAccountDropdown.isDisplayed() &&
    			welcomeMessageInMyAccountDropdown.isDisplayed() && 
    			isWelcomeMessagePatternMatches &&
    			myDetailsInMyAccountDropdown.isDisplayed() &&
    			signOutInMyAccountDropdown.isDisplayed();
    }
    
    public boolean isRewardsDisplayedInMyAccountDropDown(){
    	PropertyReader reader = PropertyReader.getPropertyReader();
        if (!reader.getProperty("environment").equalsIgnoreCase("production")){
        	if(myAccountDropdownOpened.getText().contains("J.CREW CREDIT CARD")){
        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(rewardsSectionInMyAccountDropdown));
        		return rewardsSectionInMyAccountDropdown.isDisplayed();
        	}
        	else{
        		return false;
        	}
        }
        else{
        	return true;
        }
    }
    
    public boolean isRewardsInfoDisplayedInMyAccountDropDown(){
    	
    	PropertyReader reader = PropertyReader.getPropertyReader();
        if (!reader.getProperty("environment").equalsIgnoreCase("production")){
	    	LocalDate today = LocalDate.now();
	    	
	    	String expectedDateString = "As of " + today.getMonth().name() + " " + (today.getDayOfMonth() - 1) + ", " + today.getYear() + ":";    	
	    	String dateInPage = rewardsSectionInMyAccountDropdown.findElement(By.xpath(".//p[1]")).getText();
	    	boolean isDateMatches = dateInPage.equalsIgnoreCase(expectedDateString);
	    	
	    	String rewardsCardBalance = rewardsSectionInMyAccountDropdown.findElement(By.xpath(".//p[2]")).getText();
	    	boolean isRewardBalanceMatches = rewardsCardBalance.matches("^Rewards card balance: \\$\\d+(\\.\\d+)?");
	    	
	    	String totalPoints = rewardsSectionInMyAccountDropdown.findElement(By.xpath(".//p[3]")).getText();
	    	boolean isTotalPointsMatches = totalPoints.matches("^Total points: \\d+");
	    	
	    	String pointsToNextReward = rewardsSectionInMyAccountDropdown.findElement(By.xpath(".//p[4]")).getText();
	    	boolean isPointsToNextRewardMatches = pointsToNextReward.matches("^Points to next reward: \\d+");
	    	
	    	WebElement manageMyAccountLinkInMyAccountDropdown = getManageMyAccountElementInMyAccountDropdown();
	    	
	    	return isDateMatches && isRewardBalanceMatches && isTotalPointsMatches && isPointsToNextRewardMatches && manageMyAccountLinkInMyAccountDropdown.isDisplayed();
        }
        else{
        	return true;
        }
    }
    
    public WebElement getManageMyAccountElementInMyAccountDropdown(){
    	return rewardsSectionInMyAccountDropdown.findElement(By.xpath(".//p[5]"));
    }
    
    public void clickElementFromMyAccountDropdown(String myAccountDropdownElementName){
    	WebElement element = null;
    	switch(myAccountDropdownElementName.toUpperCase()){
    		case "SIGN OUT":
    			element = signOutInMyAccountDropdown;
    			break;
    		case "MY DETAILS":
    			element = myDetailsInMyAccountDropdown;
    			break;
    		case "CLOSE":
    			element = closeIconInMyAccountDropdown;
    			break;
    		case "MANAGE YOUR ACCOUNT":
    			element = getManageMyAccountElementInMyAccountDropdown();
    			break;
    	}
    	
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    	element.click();
    	logger.info("'{}' link is clicked from My Account dropdown...", myAccountDropdownElementName.toUpperCase());
    	Util.waitLoadingBar(driver);
    }
}