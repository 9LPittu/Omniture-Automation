package testscripts.scenario1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class JCrewSubCategoryPage  {
	
	WebDriver driver;
	
	
	public static String URL="";
	
		
	@FindBy(className="product__list")
	WebElement productList;
	
	
	@FindBy(className="c-product-tile")
	WebElement productListElements;
	
	
	@FindBy(css="#c-product__list>div:nth-child(1)>div>div:nth-child(6)")
	WebElement productListElementChild;
	
	@FindBy(css="#c-category__filters>div>h3")
	WebElement refineBy;
	
	
	public JCrewSubCategoryPage(WebDriver driver){
		 
        this.driver = driver;
 
        //This initElements method will create all WebElements
 
        PageFactory.initElements(driver, this);
 
    }
	
	

	
	public WebElement getChildElement(){
		
		return productListElementChild;
	}
	
public WebElement getProductToSelect(int subCategory,int productName){	
	By cssSelector = By.cssSelector("#c-product__list>div:nth-child("+ subCategory +")>div>div:nth-child("+ productName +")>div>div.c-product__photos>a>img");
	
	
	return driver.findElement(cssSelector);
	}


	public  String getSubCategoryHeaderString(){
		
		String headerText = getFirstWord ( this.productList.getText());

		headerText = getFirstWordWithoutEnter ( this.productList.getText());
		
		return headerText;
	}
	
	public WebElement getRefineBy(){
		
		return refineBy;
	}
	
	 private String getFirstWord(String text) {
		    if (text.indexOf(' ') > -1) { // Check if there is more than one word.
		      return text.substring(0, text.indexOf(' ')); // Extract first word.
		    } else {
		      return text; // Text is the first word itself.
		    }
	 }
	 
	 private String getFirstWordWithoutEnter(String text) {
		    if (text.indexOf('\n') > -1) { // Check if there is more than one word.
		      return text.substring(0, text.indexOf('\n')); // Extract first word.
		    } else {
		      return text; // Text is the first word itself.
		    }
	 }
	 
}
