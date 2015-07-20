package functionallibraries;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import allocator.Allocator;
import supportlibraries.ElementsAction;




public class JCrewProductDetailPage {
	
	WebDriver driver;

	public static String URL="";		
	
	@FindBy(xpath="//*[@id='c-product__sizes']/div/div/ul/li[1]/span")
	public WebElement size;

	@FindBy(css="#c-product__actions>div.product__message.message.message--box.message--is-backordered")
	public WebElement backOrdered;
	
	@FindBy(css="#c-product__actions>div.product__message.message.message--is-low-inventory.is-emphasized")
	public WebElement onlyFewItemsLeft;
	
	@FindBy(css="#c-product__vps")
	public WebElement vpsMessage;
	
	@FindBy(css="#c-product__actions>div.product__message.message.message--box.message--is-final-sale")
	public WebElement isFinalSale;
	
	@FindBy(css="#c-product__size-fit>div>a")
	public WebElement sizeChart;
	
	
	@FindBy(css="#c-product__actions>div.product__message.message.message--box.message-box--is-overlay.message--no-size.is-important.is-emphasized.is-centered")
	public WebElement sizeErrMsg;

	@FindBy(xpath=".//*[@class='dropdown dropdown--quantity js-product__quantity']")	
	public WebElement selectQuantity;
		
	@FindBy(css="#c-product__actions>div.product__message.message.message--box.message-box--is-overlay.is-centered")
	public WebElement soldOutMsg;		
	
	@FindBy(css="#btn__add-to-bag")
	public WebElement addToBagBtn;
	
	@FindBy(css="#btn__add-to-bag")
	public WebElement outofStockText;
	
	@FindBy(css="#c-product__recommendations>div>ul>li:nth-child(1)>a>img")
	public WebElement customersAlsoLove;
	
	@FindBy(css="#btn__checkout")
	public WebElement checkOut;
	
	
	public JCrewProductDetailPage()
	{	
		if(Allocator.ToolName.equalsIgnoreCase("Selenium_Desktop"))
			this.driver = ElementsAction.webdriver;
		else if(Allocator.ToolName.equalsIgnoreCase("Appium"))
			this.driver = ElementsAction.appiumdriver;
		
        PageFactory.initElements(driver, this);
    }			 
}
