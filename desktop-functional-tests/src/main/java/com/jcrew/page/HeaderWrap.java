package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.page.header.GlobalPromo;
import com.jcrew.page.header.HeaderLogo;
import com.jcrew.page.header.TopNav;
import com.jcrew.pojo.Product;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
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

import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrap extends PageObject {

	private final Logger logger = LoggerFactory.getLogger(HeaderWrap.class);
	public final StateHolder stateHolder = StateHolder.getInstance();

	@FindBy(xpath = "//li[@class='primary-nav__item primary-nav__item--menu']/a")
	private WebElement menu;
	@FindBy(xpath = "//li[@class='primary-nav__item primary-nav__item--stores']/a")
	private WebElement stores;
	@FindBy(id = "c-header__userpanel")
	private WebElement sign_in;
	@FindBy(id = "c-header__userpanelrecognized")
	private WebElement myAccount;
	@FindBy(id = "js-header__cart")
	private WebElement bag;
	@FindBy(id = "c-header__userpanel")
	private WebElement userPanel;
	@FindBy(id = "c-header__minibag")
	private WebElement minibag;
	@FindBy(id = "global__header")
	private WebElement global_header;
	@FindBy(id = "global__nav")
	private WebElement global_nav;
	@FindBy(xpath = ".//div[@id='c-header__factory-link']/a")
	private WebElement lookingForFactoryLinkInHeader;
	@FindBy(xpath = "//span[@class='btn__label' and text()='BACK']")
	private WebElement hamburger_back;

	private WebElement dropdown;

	public HeaderWrap(WebDriver driver) {
        super(driver);
        GlobalPromo promo = new GlobalPromo(driver);

		reload();
	}

	public void reload() {
		try {
			Util.waitForPageFullyLoaded(driver);
			
			wait.until(new Predicate<WebDriver>(){
				@Override
				public boolean apply(WebDriver driver) {
					boolean result = false;
					if(global_header.isDisplayed() && bag.isDisplayed()){
						result = true;
					}
					return result;
				}				
			});

			wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(global_header)));
			wait.until(ExpectedConditions.visibilityOf(global_header));
			wait.until(ExpectedConditions.visibilityOf(bag));
		} catch (TimeoutException timeout) {
			logger.debug("Timed out while waiting for header in page: {}", driver.getCurrentUrl());
			Logs errorLog = driver.manage().logs();
			LogEntries errors = errorLog.get(LogType.BROWSER);

			for (LogEntry error : errors) {
				logger.error("Browser logged: {}", error);
			}
		}
	}

	public void openMenu() {
		PageFactory.initElements(driver, this);

		// this is a temparory step - hover on any Gender landing link from top
		// navigation
		hoverOverIcon("gender landing");

		if (!global_nav.isDisplayed()) {
			try {
				menu.click();
			} catch (Exception e) {
				global_header.findElement(By.xpath("//a[@class='js-primary-nav__link--menu']")).click();
			}
			wait.until(ExpectedConditions.visibilityOf(global_nav));
		}
	}

	public void clickStores(){
        wait.until(ExpectedConditions.visibilityOf(stores));
        stores.click();
    }

	public void clickSignIn() {
		int cntr = 0;
		do{
			try{
				Util.waitLoadingBar(driver);
				Util.waitForPageFullyLoaded(driver);
				Util.createWebDriverWait(driver, Util.DEFAULT_TIMEOUT/3).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(sign_in)));
				Util.createWebDriverWait(driver, Util.DEFAULT_TIMEOUT/3).until(ExpectedConditions.visibilityOf(sign_in));
				WebElement signInLink = sign_in.findElement(By.tagName("a"));
				signInLink.click();
				Util.createWebDriverWait(driver, Util.DEFAULT_TIMEOUT/3).until(ExpectedConditions.urlContains("/r/login"));
				break;
			}
			catch(StaleElementReferenceException sere){
				cntr++;
			}
			catch(TimeoutException toe){
				cntr++;
			}			
		}while(cntr<=2);		
	}

	public void clickBag() {
		bag.click();
	}
    public void myAccount(){
        wait.until(ExpectedConditions.visibilityOf(myAccount));
        myAccount.click();
    }
    public boolean isMyAccountDropdownDisplayed(){
        dropdown = userPanel.findElement(By.tagName("dl"));
        return dropdown.isDisplayed();
    }
	public boolean bagHasProducts() {
		WebElement bagContainer = bag.findElement(By.xpath(".//parent::li"));
		String classString = bagContainer.getAttribute("class");
		return classString.contains("primary-nav__item--bag-filled");
	}

	public void hoverOverIcon(String icon) {

		if ("bag".equalsIgnoreCase(icon)) {

			PropertyReader propertyReader = PropertyReader.getPropertyReader();
			String browser = propertyReader.getProperty("browser");

			if ("chrome".equals(browser) || "firefox".equals(browser)) {
				hoverAction.moveToElement(bag);
				hoverAction.perform();

				wait.until(ExpectedConditions.visibilityOf(minibag));
			} else {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("jcrew.jQuery('.primary-nav__item--bag-filled').trigger('mouseenter');");
			}

		} else if ("my account".equalsIgnoreCase(icon)) {
			wait.until(ExpectedConditions.visibilityOf(myAccount));
			hoverAction.moveToElement(myAccount);
			hoverAction.perform();

		} else if ("logo".equalsIgnoreCase(icon)) {
            HeaderLogo logo = new HeaderLogo(driver);
            logo.hoverLogo();

		} else if ("gender landing".equalsIgnoreCase(icon)) {
            TopNav topNav = new TopNav(driver);
            topNav.hoverCategory("men");

		} else if ("stores".equalsIgnoreCase(icon)) {
			wait.until(ExpectedConditions.visibilityOf(stores));
			hoverAction.moveToElement(stores);
			hoverAction.perform();
		}
	}

	public String getWelcomeMessage() {
		dropdown = userPanel.findElement(By.tagName("dl"));
		WebElement welcomeRow = dropdown.findElement(By.xpath(".//dd[@class='c-nav__userpanel--welcomeuser']"));
		String message = welcomeRow.getText();

		if (message.isEmpty()) {
			hoverOverIcon("my account");
			message = welcomeRow.getText();
		}

		hoverOverIcon("logo");

		return message;
	}

	public void goToMyDetailsDropDownMenu(String option) {
		
		String url = driver.getCurrentUrl();
		
		int cntr = 0;
		WebElement optionElement = null;
		
		do{
			try{
				hoverOverIcon("my account");
				Util.wait(1000);
				dropdown = userPanel.findElement(By.tagName("dl"));
				if(dropdown.isDisplayed())
					break;
				
				cntr++;
			}
			catch(NoSuchElementException nsee){
				logger.info("NoSuchElementException is thrown when tried to click on {} option", option);
				cntr++;
			}
			catch(ElementNotVisibleException enve){
				logger.info("ElementNotVisibleException is thrown when tried to click on {} option", option);
				cntr++;
			}
		}while(cntr<5);
			
		optionElement = dropdown.findElement(By.xpath(".//a[" + Util.xpathGetTextLower + "='" + option.toLowerCase() + "']"));
		optionElement.click();			
		
		Util.waitLoadingBar(driver);

		if ("sign out".equalsIgnoreCase(option)) {
			List<Product> bag = stateHolder.getList("toBag");
			stateHolder.put("userBag", bag);
			stateHolder.remove("toBag");

			wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
		}
	}

	public boolean isSignInVisible() {
		boolean result = false;
		wait.until(ExpectedConditions.visibilityOf(sign_in));
		List<WebElement> signInLink = sign_in.findElements(By.tagName("a"));

		if (signInLink.size() == 1)
			result = true;

		return result;
	}

	public void waitUntilNoCheckOutDropdown() {

		wait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver driver) {
				return !minibag.isDisplayed();
			}
		});

	}

	public void waitUntilCheckOutDropdown() {

		wait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver driver) {
				if(minibag.isDisplayed()){
					return true;
				}else{
					Util.scrollToElement(driver, bag);
					return false;
				}					
			}
		});

	}

	public int getItemsInBag() {
		wait.until(ExpectedConditions.visibilityOf(bag));
		WebElement cart_size = bag.findElement(By.className("js-cart-size"));
		String cartSizeText = cart_size.getText().trim();

		if (cartSizeText.isEmpty())
			cartSizeText = "0";

		cartSizeText = cartSizeText.replaceAll("[^0-9]", "");

		return Integer.parseInt(cartSizeText);
	}

}
