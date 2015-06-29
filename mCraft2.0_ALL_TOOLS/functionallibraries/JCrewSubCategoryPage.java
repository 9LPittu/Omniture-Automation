package functionallibraries;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import supportlibraries.ElementsAction;
import allocator.Allocator;


public class JCrewSubCategoryPage  {
	
	public WebDriver driver;	
	
	public static String URL="";
		
	@FindBy(className="product__list")
	public WebElement productList;
		
	@FindBy(className="c-product-tile")
	public WebElement productListElements;
		
	@FindBy(css="#c-product__list>div:nth-child(1)>div>div:nth-child(6)")
	public WebElement productListElementChild;
	
	@FindBy(css="#c-header>div>h2")	
	public WebElement subCategoryHeader;
	
	@FindBy(css="h3.accordian__header.js-accordian__header")
	public	WebElement refineBy;
	

	public JCrewSubCategoryPage()
	{		 
		if(Allocator.ToolName.equalsIgnoreCase("Selenium_Desktop"))
			this.driver = ElementsAction.webdriver;
		else if(Allocator.ToolName.equalsIgnoreCase("Appium"))
			this.driver = ElementsAction.appiumdriver;
 
        PageFactory.initElements(driver, this);
    }
	
	
public WebElement getProductToSelect(int subCategory,int productName)
{
	By cssSelector = By.cssSelector("#c-product__list>div:nth-child("+ subCategory +")>div>div:nth-child("+ productName +")>div>div.c-product__photos>a>img");	
	return driver.findElement(cssSelector);
}


public WebElement getRefineBySubCategory(int subCategory){	
	subCategory = subCategory +1;
	By subCategoryText = By.cssSelector("#c-category__filters>div>ul>li:nth-child("+subCategory+")>a");
	return driver.findElement(subCategoryText);
	}

	
	public WebElement getRefineBy(){
		
		return refineBy;
	}		 
}
