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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Header {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Header.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String menuItems[] = {"MENU", "SEARCH", "SIGN IN", "BAG"};

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
    
    @FindBy(xpath = "//li[@id='c-header__userpanel']/a/span[contains(text(),'sign in')]")
    private WebElement signInFromHeader;

    @FindBy(xpath = "//li[@id='c-header__userpanel']/a/span[@class='primary-nav__text']")
    private WebElement myAccountFromHeader;

    @FindBy(xpath = "//div[@id='global__promo']/section")
    private WebElement globalPromoPanel;
    
    @FindBy(xpath="//input[contains(@class,'js-header__search__input')]")
    private WebElement headerSearchInput;
    
    @FindBy(id = "global__header")
    private WebElement global_header;
    
    @FindBy(id = "global__promo")
	private WebElement global_promo;

    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Calling this method means promo disclaimer is expected
    public String ValidatePromoDisclaimer(int index) {

        List<WebElement> promos = globalPromoPanel.findElements(By.xpath(".//div[contains(@class, 'header__promo__align')]"));
        WebElement promo = promos.get(index);

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(promo));

        WebElement detailLink = promo.findElement(By.xpath(".//a[contains(@class,'js-header__promo__btn--details')]")); //expand disclaimer
        detailLink.click();
        
        String disclaimer = "";
        List<WebElement> disclaimerElements = globalPromoPanel.findElements(By.xpath(".//div[@class='js-header__promo__details-pushdown header__promo__details-pushdown']"));
        for(int i=0;i<disclaimerElements.size();i++){
        	disclaimer = disclaimerElements.get(i).getText();
        	if(!disclaimer.isEmpty()){
        		break;
        	}
        }
        
        logger.debug("Promo disclaimer {}", disclaimer);
        detailLink.click();

        return disclaimer;
    }

    public List<String> getPromoListText() {
        List<String> promosText = new ArrayList<String>();
        List<WebElement> promos = globalPromoPanel.findElements(By.xpath(".//div[contains(@class, 'header__promo__align')]"));

        for (WebElement promo : promos) {
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(promo));
            promosText.add(promo.getText());
        }
        return promosText;
    }


    public boolean isHeaderLinkPresent(String headerLink) {

    	try{
	    	logger.debug("Checking for header link: {}", headerLink);
	    	Util.waitForPageFullyLoaded(driver);
	    	Util.waitLoadingBar(driver);
	    	
	    	int i =0;
	    	int timeOut = Util.getDefaultTimeOutValue()/3;
	    	while(i<=2){
	    		try{
	    			Util.createWebDriverWait(driver,timeOut).until(ExpectedConditions.visibilityOf(headerWrap));
	    			
	    	        WebElement headerLinkElement = Util.createWebDriverWait(driver,timeOut).until(
	    	                ExpectedConditions.elementToBeClickable(
	    	                		By.xpath("//span[contains(@class,'primary-nav__text') and " + Util.xpathGetTextLower + "='" + headerLink.toLowerCase() + "']")));
	    	        
	    	        Util.createWebDriverWait(driver,timeOut).until(
	    	        				ExpectedConditions.elementToBeClickable(headerLinkElement));
	    	        return true;
	    		}
	    		catch(Exception e){
	    			i++;
	    		}
	    	}
			
	        return false;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}

    }

    public String getHeaderLinkHref(String headerLink) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerWrap));
        WebElement headerLinkElement = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@class,'primary-nav__text') and " + Util.xpathGetTextLower + "='" + headerLink.toLowerCase() + "']/parent::a")));
        return headerLinkElement.getAttribute("href");
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
    	try{
	        WebElement headerSearchInput = Util.createWebDriverWait(driver).until(
	                ExpectedConditions.visibilityOf(headerWrap.findElement(By.className("header__search__input"))));
	        return headerSearchInput.isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
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
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("footer__country-context")));
        Util.clickWithStaleRetry(headerLogo);
    }
    
    public boolean isLogoVisible() {
        WebElement logo = global_header.findElement(By.className("c-header__logo"));
        return logo.isDisplayed();
    }

    public void click_on_search_button() {
    	int cntr=0;
    	do{
    		try{
                Util.createWebDriverWait(driver, Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
    	        Util.createWebDriverWait(driver, Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.visibilityOfElementLocated(By.className("footer__country-context")));
    	        Util.createWebDriverWait(driver, Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.elementToBeClickable(searchButton));
    	        searchButton.click();
    	        
    	        if(headerSearchInput.isDisplayed()){
    	        	break;
    	        } else {
                    cntr++;
                }
    		}
    		catch(Exception e){
    			cntr++;
    		}
    	}while(cntr<3);
    	
    	if(!headerSearchInput.isDisplayed()){
        	throw new WebDriverException("Search Input field is not displayed!!!");
        }
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
        WebElement link = Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='c-header__userpanel']/a")));
        return link.isDisplayed();
    }

    public boolean isMyAccountPresent() {
        WebElement link = Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='c-nav__userpanel']/*[@id='c-header__userpanelrecognized']")));
        return link.isDisplayed();
    }

    public void click_on_sign_in() {
        WebElement link = Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='c-header__userpanel']/a")));
        link.click();
    }

    public void click_item_bag() {
    	Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(shoppingBagLink)));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(shoppingBagLink));
        shoppingBagLink.click();
    }

    public boolean isBagLinkDisplaying() {
    	try{
    		return shoppingBagLink.isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }

    public void click_breadcrumb(String breadcrumb) {
        Util.waitWithStaleRetry(driver, breadcrumbSection);
        WebElement breadcrumbElement = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='" + breadcrumb + "' and contains(@class,'breadcrumb__link')]")));
        
        try{
        	breadcrumbElement.click();
        }
        catch(WebDriverException wde){
        	((JavascriptExecutor)driver).executeScript("arguments[0].click();", breadcrumbElement);
        }
    }

    public boolean isGenderLandingPage(String gender) {
        Country country = (Country) stateHolder.get("context");
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(genderLandingSection));
        WebElement genderPageElement = genderLandingSection.findElement(By.xpath(".//h2[contains(text(),'NEW FOR " + gender.toUpperCase() + "')]"));

        boolean isDisplayed = genderPageElement.isDisplayed();
        boolean isURL = Util.countryContextURLCompliance(driver, country);

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

    public boolean isAllMenuItemsDisplayed() {
        boolean result = true;

        for (String item : menuItems) {
            result &= isHeaderLinkPresent(item);
        }

        return result;
    }

    public String getBagButtonLink() {
        return shoppingBagLink.getAttribute("href");
    }

    public String getStoresButtonLink() {
        WebElement stores = driver.findElement(By.cssSelector(".primary-nav__item--stores > .primary-nav__link"));
        return stores.getAttribute("href");
    }

    public void clickElementFromHeader(String elementName) {
        WebElement headerElement = null;
        switch (elementName.toUpperCase()) {
            case "SIGN IN":
                headerElement = signInFromHeader;
                break;
            case "MY ACCOUNT":
                headerElement = myAccountFromHeader;
                break;
        }

        Util.waitWithStaleRetry(driver, headerElement);
        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(headerElement));
        } catch (ElementNotVisibleException e) {
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerElement));
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(headerElement));
        }
        headerElement.click();
        logger.info("'{}' link is clicked from header...", elementName);
        Util.waitLoadingBar(driver);
    }
    
    public int getItemsInBag() {
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(global_promo));
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shoppingBagLink));
		WebElement cart_size = shoppingBagLink.findElement(By.className("js-cart-size"));
		String cartSizeText = cart_size.getText().trim();

		if (cartSizeText.isEmpty())
			cartSizeText = "0";

		cartSizeText = cartSizeText.replaceAll("[^0-9]", "");

		return Integer.parseInt(cartSizeText);
	}
}