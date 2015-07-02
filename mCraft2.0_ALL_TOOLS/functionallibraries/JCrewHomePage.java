package functionallibraries;

import io.appium.java_client.AppiumDriver;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import supportlibraries.ElementsAction;
import allocator.Allocator;


public  class JCrewHomePage  {
	
	public WebDriver driver= ElementsAction.webdriver ;
	public AppiumDriver driver1=ElementsAction.appiumdriver;
	
	public static String URL="";
	
	@FindBy(xpath=".//*[@class='js-primary-nav__link--menu primary-nav__link']")
	public WebElement hamburgerMenu;	
	
	
	// Search Objects
	@FindBy(xpath=".//*[@class='primary-nav__text primary-nav__text--search']")
	public WebElement menuSearch;
	
	@FindBy(xpath=".//*[@class='js-header__search__input header__search__input']")
	public WebElement searchTextBox;
	
	@FindBy(className="header__search")
	public WebElement searchIcon;
	
	@FindBy(css="span.icon-searchtray.icon-searchtray-close")
	public WebElement searchClose;
		
	@FindBy(css="span.primary-nav__text.primary-nav__text--stores")
	public WebElement storesLink;
	
	@FindBy(name="subscribeEmail")
	public WebElement emailTextBox;
		
	@FindBy(css="button.footer__signup__button.js-footer__submit-button")
	public WebElement signupBtn;
	
	@FindBy(css="#global__footer>div>div.footer__row.footer__row--main>div>div.footer__column.footer__column--signup.js-footer__column--signup>form>fieldset>div")	
	public WebElement emailSuccess;
	
	@FindBy(className="is-important")
	public WebElement emailFail;
	
	
	//Let Us Help You - Objects			
	@FindBy(className="footer__help__link")
	public List<WebElement> helpLinks;
	
	//Social Icon
	@FindBy(className="footer__social__link")
	public List<WebElement> socialIcons;
		
 	@FindBy(css="#global__nav")
	public WebElement deptList;
	
	@FindBy(className="department-nav__list")
	public WebElement deptHeaderList;

	@FindBy(className="global__nav")	
	public WebElement categoryList;
	
	@FindBy(className="footer__item__link")
	public List<WebElement> footerLinks;
		
	public void deptClick(String linkText)
	{
		ElementsAction.linkText(linkText, "Click", "");		
	}
	
	public void categoryClick(String linkText)
	{
		ElementsAction.linkText(linkText, "Click", "");
	}	
	
	
	public JCrewHomePage()
	{
		if(Allocator.ToolName.equalsIgnoreCase("Selenium_Desktop"))
			this.driver = ElementsAction.webdriver;
		else if(Allocator.ToolName.equalsIgnoreCase("Appium"))
			this.driver = ElementsAction.appiumdriver;
 
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }
	
public String getCurrentURL(){		
	String getURL = driver.getCurrentUrl();	
	return getURL;
}

public List<WebElement> getCategoryList(){
	List<WebElement> elementsList =  driver.findElements(By.className("global__nav"));
	return elementsList;
}

public WebElement getLetUsHelpYou(int footerHelp)
{
	WebElement helpLinks = driver.findElement(By.cssSelector("#global__footer>div>div.footer__row.footer__row--top>div>div.c-footer__help>ul>li:nth-child("+footerHelp+")>a"));	
	return helpLinks;
}

public WebElement getFooterLinks(int strFirst, int strSecond)
{
	WebElement footerLinks = driver.findElement(By.cssSelector("#global__footer>div>div.footer__row.footer__row--main>div>div:nth-child(2)>ul:nth-child("+strFirst+")>li:nth-child("+strSecond+")>a"));	
	return footerLinks;
}
}
