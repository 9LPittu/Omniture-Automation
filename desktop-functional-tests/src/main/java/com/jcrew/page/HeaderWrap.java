package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.pojo.Product;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrap {

	private final WebDriver driver;
	private final Logger logger = LoggerFactory.getLogger(HeaderWrap.class);
	public final StateHolder stateHolder = StateHolder.getInstance();
	private final WebDriverWait wait;
	private final Actions hoverAction;
	TestDataReader testdataReader = TestDataReader.getTestDataReader();
	PropertyReader propertyReader  = PropertyReader.getPropertyReader(); 

	@FindBy(xpath = "//li[@class='primary-nav__item primary-nav__item--menu']/a")
	private WebElement menu;
	@FindBy(xpath = "//li[@class='primary-nav__item primary-nav__item--search']/a")
	private WebElement search;
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
	@FindBy(id = "global__promo")
	private WebElement global_promo;
	@FindBy(id = "c-header__minibag")
	private WebElement minibag;
	@FindBy(id = "global__header")
	private WebElement global_header;
	@FindBy(id = "global__nav")
	private WebElement global_nav;
	@FindBy(id = "js-header__logo")
	private WebElement header_logo;
	@FindBy(className = "header__department-nav")
	private WebElement top_nav;
	@FindBy(xpath = ".//div[@id='c-header__factory-link']/a")
	private WebElement lookingForFactoryLinkInHeader;
	@FindBy(xpath = "//span[@class='btn__label' and text()='BACK']")
	private WebElement hamburger_back;

	private WebElement dropdown;

	public HeaderWrap(WebDriver driver) {
		this.driver = driver;
		this.hoverAction = new Actions(driver);
		this.wait = Util.createWebDriverWait(driver);

		PageFactory.initElements(driver, this);

		reload();
	}

	public void reload() {
		try {
			wait.until(ExpectedConditions.visibilityOf(global_promo));
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

	public void clickLogo() {
		if (header_logo.isDisplayed()) {
			header_logo.click();
		} else {
			clickBreadCrumb("J.Crew");
		}

		HomePage homePage = new HomePage(driver);
		logger.debug("We are in homepage after clicking logo or breadcrumb: {}", homePage.isHomePage());
	}

	public void clickBreadCrumb(String text) {
		WebElement breadCrumb = global_header
				.findElement(By.xpath(".//a[@class='breadcrumb__link' and text()='" + text + "']"));
		breadCrumb.click();
	}

	public void searchFor(String searchItem) {
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
		String env = propertyReader.getProperty("environment");

		if (testdataReader.hasProperty(env + "." + searchItem)) {
			searchItem = testdataReader.getData(env + "." + searchItem);
		}

		searchForSpecificTerm(searchItem);

		String currentUrl = driver.getCurrentUrl();
		if (currentUrl.contains("/r/search")) {
			ArraySearch searchArray = new ArraySearch(driver);
			searchArray.selectRandomProduct();
		}
	}

	public void searchForSpecificTerm(String searchTerm) {
		wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(minibag)));
		search.click();
		WebElement searchHeader = global_header.findElement(By.className("header__search__wrap"));
		WebElement searchInput = searchHeader
				.findElement(By.xpath(".//input[contains(@class,'js-header__search__input')]"));
		searchInput.clear();
		searchInput.sendKeys(searchTerm);
		WebElement searchButton = searchHeader
				.findElement(By.xpath(".//a[contains(@class, 'js-header__search__button')]"));
		searchButton.click();
		logger.info("Searching for {}", searchTerm);
		Util.waitLoadingBar(driver);
	}

	public void clickSignIn() {
		wait.until(ExpectedConditions.visibilityOf(sign_in));
		WebElement signInLink = sign_in.findElement(By.tagName("a"));
		signInLink.click();
	}

	public void clickBag() {
		bag.click();
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

			WebElement logo = global_header.findElement(By.className("c-header__logo"));
			String logoClass = logo.getAttribute("class");

			if (logoClass.contains("is-hidden")) {
				logo = global_header.findElement(By.className("c-header__breadcrumb"));
			}

			hoverAction.moveToElement(logo);
			hoverAction.perform();
		} else if ("gender landing".equalsIgnoreCase(icon)) {
			WebElement logo = top_nav.findElement(By.xpath(
					"//span[contains(@class, 'department-nav__text') and " + Util.xpathGetTextLower + " = 'men']"));
			hoverAction.moveToElement(logo);
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
		hoverOverIcon("my account");
		dropdown = userPanel.findElement(By.tagName("dl"));
		WebElement optionElement = dropdown.findElement(By.linkText(option));

		String url = driver.getCurrentUrl();
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
				logger.info("minibag class: {}", minibag.getAttribute("class"));
				return !minibag.isDisplayed();
			}
		});

	}

	public void waitUntilCheckOutDropdown() {

		wait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver driver) {
				logger.info("minibag class: {}", minibag.getAttribute("class"));
				return minibag.isDisplayed();
			}
		});

	}

	public void clickDeptLinkFromTopNav(String dept) {
		String url = driver.getCurrentUrl();
		top_nav.findElement(By.xpath("//span[contains(@class, 'department-nav__text') and " + Util.xpathGetTextLower
				+ " = '" + dept.toLowerCase() + "']")).click();

		if (!"view all".equals(dept))
			wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));

		reload();
	}

	public List<String> getTopNavOptions() {
		List<WebElement> options = top_nav.findElements(By.className("department-nav__item"));
		List<String> optionsString = new ArrayList<>(options.size());

		for (WebElement option : options) {
			optionsString.add(option.getText().toLowerCase());
		}

		return optionsString;
	}

	public int getItemsInBag() {
		wait.until(ExpectedConditions.visibilityOf(global_promo));
		wait.until(ExpectedConditions.visibilityOf(bag));
		WebElement cart_size = bag.findElement(By.className("js-cart-size"));
		String cartSizeText = cart_size.getText().trim();

		if (cartSizeText.isEmpty())
			cartSizeText = "0";

		cartSizeText = cartSizeText.replaceAll("[^0-9]", "");

		return Integer.parseInt(cartSizeText);
	}

	public boolean isLogoVisible() {
		WebElement logo = global_header.findElement(By.className("c-header__logo"));
		return logo.isDisplayed();
	}

	public void clickBack() {
		if (hamburger_back.isDisplayed()) {
			hamburger_back.click();
		} else {

			clickLogo();
			openMenu();
		}
	}

	public void hoverCategory(List<String> categories) {
		int index = Util.randomIndex(categories.size());
		String randomCategory = categories.get(index);

		hoverCategory(randomCategory);
	}

	public void hoverCategory(String selectedCategory) {
		wait.until(ExpectedConditions.visibilityOf(top_nav));

		WebElement categoryLink = top_nav.findElement(By.xpath(
				".//a[@class='department-nav__link' and contains(@name,'>>" + selectedCategory.toLowerCase() + "')]"));
		hoverAction.moveToElement(categoryLink);
		hoverAction.perform();
	}

	public void selectSubCategory() {
		String clothClassName = propertyReader.getProperty("clothing.className");
		String shoesClassName = propertyReader.getProperty("shoes.className");
	
    	WebElement holder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(@class,'department-subcat-nav__wrap "
        		+ "js-department-subcat-nav__wrap js-expand-subcat-nav is-visible')]")));
    
        List<WebElement> subCategories = holder.findElements(By.xpath(".//div[contains(@class,'" + clothClassName + "') or contains(@class,'" + shoesClassName + "')]/div/ul/li/a"));
        
        WebElement selectedSubCategory = Util.randomIndex(subCategories);

        logger.info("Selected subcategory: {}", selectedSubCategory.getText());
        selectedSubCategory.click();

        Util.waitLoadingBar(driver);
        hoverOverIcon("logo");
        
    }

	public void selectSubCategory(String subCategory) {
		WebElement holder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(@class,'department-subcat-nav__wrap "
        		+ "js-department-subcat-nav__wrap js-expand-subcat-nav is-visible')]")));
    
        WebElement subCategoryElement = holder.findElement(By.xpath(".//ul/li/a[" + Util.xpathGetTextLower + "='" + subCategory.toLowerCase() + "']"));

        logger.info("Selected subcategory: {}", subCategoryElement.getText());
        subCategoryElement.click();

        Util.waitLoadingBar(driver);
        hoverOverIcon("logo");
	}
}
