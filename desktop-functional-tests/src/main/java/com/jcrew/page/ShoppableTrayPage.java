package com.jcrew.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.jcrew.utils.Util;

public class ShoppableTrayPage extends PageObject {
	 @FindBy(id = ".//span[text()='shop now']")
	    private List<WebElement> MpdarrayContainer;
	 @FindBy(xpath=".//h1[@class='header__tray']")
	    private WebElement shopthelooktitle;
	 @FindBy(xpath=".//div[@class='tray--count']")
	    private WebElement shopthelookitemcount;
	 @FindBy(xpath=".//li[contains(@class,'js-tray__item tray-list__item')]")
	 private List<WebElement> shopthelookitemslist;
	 
	 @FindBy(xpath=".//li[contains(@class,'js-pagination__item--previous')]")
	 private WebElement previousButton;
	 @FindBy(xpath=".//li[contains(@class,'js-pagination__item--next')]")
	 private WebElement nextButton;
	 
	 @FindBy(xpath=".//*[@class='product__name']")
	 private WebElement producName;
	 
	 @FindBy(xpath=".//section[@class='c-product__code']")
	 private WebElement itemNumber;
	 
	 @FindBy(className="product__price--list")
	 private WebElement itemPrice;
	 
		 @FindBy(className = "desc_line4")
	    private List<WebElement> temp;
    public ShoppableTrayPage(WebDriver driver) {
        super(driver);
        //headerWrap = new HeaderWrap(driver);

        PageFactory.initElements(driver, this);        
       // wait.until(ExpectedConditions.visibilityOfAllElements(MpdarrayContainer));
    
    }
    
    public void selectTheRandomProductForShoppableTray(){
        Util.waitForPageFullyLoaded(driver);
      
        temp.get(1).click();
        
      // MpdarrayContainer.get(1).click();
        
 /*  if (MpdarrayContainer.size() > 0) {
           WebElement selectedSize = Util.randomIndex(MpdarrayContainer);
            Util.scrollToElement(driver, selectedSize);
            selectedSize.click();
        }*/
    }
    
    public String verifyShoppableTrayTitle(){
    	
     String title= shopthelooktitle.getText();
     
     return title;
    }
    
    // temp method for validation need to change into properites
     
    public String verifyShoppableTrayTitleTemp(){
    	
        String title= "SHOP THE LOOK";
        
        return title;
       }
    //Item count
    public int shoppableTrayItemcount(){
    	
      String str = shopthelookitemcount.getText();
      String[] part = str.split(" ");
      int itemcount =Integer.parseInt(part[0]);      
      
      return itemcount;
    
    }
    
    public int itemsCountInCarousel(){
    	
       int items= shopthelookitemslist.size();
       
       return items;
    }
    
   public void clickOnPreviousButton(){
	 if(previousButton.isEnabled()){ 
	   previousButton.click();
	 }
   }
   
   public void clickOnNextButton(){
	  if(nextButton.isEnabled()){
	   nextButton.click();
      }
    }
   
   public void itemDetails(){
	   
	   producName.isDisplayed();
	   itemNumber.isDisplayed();
	   
   }
   public void verifyAllProductDetails(){   
	
     for (WebElement item : shopthelookitemslist){
      
	   item.click();
	   
	   this.itemDetails();
	   this.clickOnNextButton();
      
     }
   }
      
}
    
	

