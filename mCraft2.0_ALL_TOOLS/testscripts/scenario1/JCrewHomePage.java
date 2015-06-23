package testscripts.scenario1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import supportlibraries.SeleniumReport;

import com.cognizant.framework.Status;


public class JCrewHomePage {
	
	WebDriver driver;
	
	

	public static String URL="";
	
	@FindBy(xpath=".//*[@class='js-primary-nav__link--menu primary-nav__link']")	
	WebElement hamburgerMenu;

	
	@FindBy(linkText="WOMEN")
	WebElement womenLink;

	@FindBy(linkText="SWEATERS")
	WebElement sweatersSubCategoryLink;
	
	// Search Objects
	@FindBy(xpath=".//*[@class='primary-nav__text primary-nav__text--search']")
	WebElement menuSearch;
	
	@FindBy(xpath=".//*[@class='js-header__search__input header__search__input']")
	WebElement searchTextBox;
	
	@FindBy(className="header__search")
	WebElement searchIcon;
	
	@FindBy(css="span.primary-nav__text.primary-nav__text--stores")
	WebElement storesLink;
	
	
	//Footer Section - Objects
	@FindBy(css="button.footer__signup__button.js-footer__submit-button")
	WebElement signupBtn;
	
	@FindBy(className="footer__signup__copy js-footer__signup__copy")
	WebElement emailSuccess;
	
	
	
    //Social Icon - Objects
	@FindBy(className="footer__social__link")
	WebElement socialFacebook;
	
		
	
	
	public WebElement getDepartmentByText(String linkText,SeleniumReport report){		
		WebElement element = null;				
		try {		
		element = driver.findElement(By.linkText(linkText));		
		}
		catch (Exception e){
			report.updateTestCaseLog(" Department " + linkText + " not Visible or problem accessing the element  ",linkText,Status.FAIL);
		}				
		return element;
	}
	
	public WebElement getCategoryByText(String linkText,SeleniumReport report){						
		WebElement element = null;		
		try {		
		element = driver.findElement(By.linkText(linkText));	
		}
		catch (Exception e){
			report.updateTestCaseLog(" Category " + linkText + " not Visible or problem accessing the element  ",linkText,Status.FAIL);
		}
		return element;
	}

	
	
	public WebElement getSearchTextBox(String searchString){
		WebElement searchText=null;		
		searchText = driver.findElement(By.xpath(".//*[@class='js-header__search__input header__search__input']"));
		searchText.sendKeys(searchString);
		return searchTextBox;
	}
	
	public JCrewHomePage(WebDriver driver){
		 
        this.driver = driver;
 
        //This initElements method will create all WebElements
 
        PageFactory.initElements(driver, this);
 
    }

	public WebElement getEmailSetupTextBox(SeleniumReport report){						
		WebElement emailSignup = null;		
		try {		
		emailSignup = driver.findElement(By.name("subscribeEmail"));	
		emailSignup.click();
		emailSignup.sendKeys("fger@ger.com");
		}
		catch (Exception e){
			report.updateTestCaseLog("Verified"," Email Sign Text box not Visible or problem accessing the element  ",Status.FAIL);
		}
		return emailSignup;
	}
	
public WebElement getSignup()
{
		return signupBtn;
}

public WebElement getStoresLink()
{
		return storesLink;
}	

public WebElement getEmailSuccess(){
	WebElement emailSuccessText=null;		
	emailSuccessText = driver.findElement(By.cssSelector("#global__footer>div>div.footer__row.footer__row--main>div>div.footer__column.footer__column--signup.js-footer__column--signup>form>fieldset>div"));
	return emailSuccessText;
}

public WebElement getEmailFail(){
	WebElement emailFailText=null;		
	emailFailText = driver.findElement(By.className("is-important"));	
	return emailFailText;
}

public String getCurrentURL(){		
	String getURL = driver.getCurrentUrl();	
	return getURL;
}


}
