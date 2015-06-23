package testscripts.scenario1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import supportlibraries.SeleniumReport;



public class JCrewProductDetailPage {
	
	WebDriver driver;
	
	

	public static String URL="";
	
	
	
	@FindBy(xpath="//*[@id='c-product__sizes']/div/div/ul/li[1]/span")
	WebElement size;

	@FindBy(css="#c-product__actions>div.product__message.message.message--box.message--is-backordered")
	WebElement backOrdered;
	
	@FindBy(css="#c-product__actions>div.product__message.message.message--is-low-inventory.is-emphasized")
	WebElement onlyFewItemsLeft;
	
	@FindBy(css="#c-product__vps")
	WebElement vpsMessage;
	
	@FindBy(css="#c-product__actions>div.product__message.message.message--box.message-box--is-overlay.message--no-size.is-important.is-emphasized.is-centered")
	WebElement sizeErrMsg;

	@FindBy(xpath=".//*[@class='dropdown dropdown--quantity js-product__quantity']")	
	WebElement selectQuantity;
		
	@FindBy(css="#c-product__actions>div.product__message.message.message--box.message-box--is-overlay.is-centered")
    WebElement soldOutMsg;		
	
	@FindBy(css="#btn__add-to-bag")
	WebElement addToBagBtn;
	
	@FindBy(css="#btn__add-to-bag")
	WebElement outofStockText;
	
	@FindBy(css="#c-product__recommendations>div>ul>li:nth-child(1)>a>img")
	WebElement customersAlsoLove;
	

	@FindBy(css="#btn__checkout")
	WebElement checkOut;
	
	
	public JCrewProductDetailPage(WebDriver driver){
		 
        this.driver = driver;
 
        //This initElements method will create all WebElements
 
        PageFactory.initElements(driver, this);
 
    }
	
	
	
	public WebElement getFirstSize(){
		
		return size;
	}
	
public WebElement getAddToBagBtn(){
		
		return addToBagBtn;
	}
	


public  String getBackorderedString(){
	
	String backorderedText =  this.backOrdered.getText();
	
	return backorderedText;
}

public  String getOutOfStockString(){
	
	String outofStockText =  this.outofStockText.getText();
	
	return outofStockText;
}

public  String getOnlyFewItemLeftString(){
	
	String OnlyFewItemsLeftText =  this.onlyFewItemsLeft.getText();
	
	return OnlyFewItemsLeftText;
}

public  String getVPSMessageString(){
	
	String VPSMessageString =  this.vpsMessage.getText();
	
	return VPSMessageString;
}

public  String getSizeErrMsg(){
	
	String sizeErrMsg =  this.sizeErrMsg.getText();
	
	return sizeErrMsg;
}


public  String getSoldOutMsg(){
	
	String soldOutMsg =  this.soldOutMsg.getText();
	
	return soldOutMsg;
}

public WebElement getCustomersAlsoLove(){
	
	return customersAlsoLove;
}

public WebElement getCheckout(){
	
	return checkOut;
}


public WebElement setQuantity(String qtyValue,SeleniumReport report){						

	WebElement selectElement = null;	
	try {		
	System.out.println("testing");
	WebElement selectQty = driver.findElement(By.xpath(".//*[@class='dropdown dropdown--quantity js-product__quantity']"));
	Select qty = new Select(selectQty);
	qty.selectByValue(qtyValue);
	}
	catch (Exception e){
		//report.updateTestCaseLog(" Category " + linkText + " not Visible or problem accessing the element  ",linkText,Status.FAIL);
	}
	return selectElement;
} 
	 
}
