package com.jcrew.page.account;

import com.jcrew.pojo.Product;
import com.jcrew.pojo.User;
import com.jcrew.utils.Util;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by msayed3 on 8/20/2016.
 */
public class FactoryAccountDetail extends Account implements IAccountDetail {

    @FindBy(className = "mainContainer")
    private WebElement accountDetailForm;
    
    @FindBy(className = "accountMainContainer")
    private WebElement accountMainContainer;
    
    @FindBy(className="leftNavContainer")
    private WebElement leftNavContainer;
    
    @FindBy(id="account_detail")
    private WebElement account_details_form;

    public FactoryAccountDetail(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(accountMainContainer));
    }

    public boolean isAccountDetailPage() {
        Util.waitLoadingBar(driver);
        wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
        return accountDetailForm.isDisplayed();
    }

    public void updateDetails(String fieldLabel, String updateType) {
    	wait.until(ExpectedConditions.visibilityOf(accountDetailForm));
        WebElement formElement = getformElement(fieldLabel);
        if ("invalid".equalsIgnoreCase(updateType)) {
            formElement.clear();
            formElement.sendKeys(Keys.TAB);
            formElement.click();
        } else {
            String oldValue = formElement.getAttribute("value");
            String newValue = "new" + oldValue;
            
            formElement.clear();
            formElement.sendKeys(newValue);
            
            if(fieldLabel.equalsIgnoreCase("email")){
            	stateHolder.put("currentEmail", newValue);
            }else if(fieldLabel.equalsIgnoreCase("re-enter email")){
            	formElement.clear();
            	String emailAddress = stateHolder.get("currentEmail");
            	formElement.sendKeys(emailAddress);
            }           
        }
    }

    public String getErrorMessage(String fieldLabel) {    	
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("erorMsg")));                
        return errorMessage.getText();
    }


    public void clickChangePassword() {
    	getformElement("change password").click();
    }

    public void fillChangePasswordFileds() {
    	String newPassword = User.getSomePassword(7);
        getformElement("old password").sendKeys((String) stateHolder.get("fakenewuserPassword"));
        getformElement("new password").sendKeys(newPassword);
        getformElement("re-enter new password").sendKeys(newPassword);
    }

    public boolean isBirthField(String btnStatus) {
    	boolean result = false;   	
    	
        if(btnStatus.equalsIgnoreCase("enabled")){
        	WebElement birthMonth = getformElement("month");
        	WebElement birthDay = getformElement("day");
        	result = birthMonth.isEnabled() && birthDay.isEnabled();
        }else{
        	try{
        		WebElement birthMonth = getformElement("month");
            	WebElement birthDay = getformElement("day");
            	result = !birthMonth.isEnabled() && !birthDay.isEnabled();
        	}catch(NoSuchElementException nsee){
        		result = true;
        	}
        }
        
        return result;
    }

    public void selectDate(String dateType, String value) {
    	WebElement element = getformElement(dateType);
    	Select select = new Select(element);
    	select.selectByVisibleText(value);
    }

    public void saveUpdates() {
    	Util.waitForPageFullyLoaded(driver);
        //getformElement("save button").click();
    	Util.scrollAndClick(driver, getformElement("save button"));
    	Util.waitForPageFullyLoaded(driver);
    }

    public String getConfirmatonMsg() {
    	WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("homecopy"))); 
        return errorMessage.getText().trim();
    }

    public String getBirthdayCopy() {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public void clickLeftNavLinks(String linkText) {
    	String url = driver.getCurrentUrl();
    	
    	try {
    		if(leftNavContainer.isDisplayed()) {
    			 wait.until(ExpectedConditions.visibilityOf(leftNavContainer));
    		        WebElement linkElement = leftNavContainer.findElement(By.xpath("//div[@class='leftNavItem']/a[contains(text(),'"+linkText+"')]"));
    		        linkElement.click();
    		}
    	}catch (Exception e) {
    		
		}
        Util.waitForPageFullyLoaded(driver);
        Util.waitLoadingBar(driver);
        
        if ("sign out".equalsIgnoreCase(linkText)) {
			List<Product> bag = stateHolder.getList("toBag");
			stateHolder.put("userBag", bag);
			stateHolder.remove("toBag");

			wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
		}
    }

    public void click_reward_link(String link) {
        throw new WebDriverException("Method not implemented for Factory");

    }

    public boolean isMenuLinkPresent(String link) {
        throw new WebDriverException("Method not implemented for Factory");
    }


    public User getUserDetails() {
    	wait.until(ExpectedConditions.visibilityOf(accountDetailForm));

        String firstName = getformElement("first name").getAttribute("value");
        String lastName = getformElement("last name").getAttribute("value");
        String email = getformElement("email").getAttribute("value");
        
        WebElement countryElement = getformElement("country");
        Select select = new Select(countryElement);
        String country = select.getFirstSelectedOption().getText();

        return new User(email, "nullPassword", firstName, lastName, country);
    }

    private WebElement getformElement(String fieldLabel) {
    	WebElement formElement = null;
        wait.until(ExpectedConditions.visibilityOf(account_details_form));
        
        switch(fieldLabel.toLowerCase()){
        	case "first name":
        		formElement = account_details_form.findElement(By.id("firstName"));
        		break;
        	case "last name":
        		formElement = account_details_form.findElement(By.id("lastName"));
        		break;
        	case "email":
        		formElement = account_details_form.findElement(By.id("emailAdd"));
        		break;        		
        	case "re-enter email":
        		formElement = account_details_form.findElement(By.id("emailAddConf"));
        		break;
        	case "country":
        		formElement = account_details_form.findElement(By.id("country"));
        		break;
        	case "month":
        		formElement = account_details_form.findElement(By.id("dob_month"));
        		break;
        	case "day":
        		formElement = account_details_form.findElement(By.id("dob_day"));
        		break;
        	case "change password":
        		formElement = account_details_form.findElement(By.xpath(".//a[text()='Create a new password']"));
        		break;
        	case "old password":
        		formElement = account_details_form.findElement(By.id("oldPassWord"));
        		break;
        	case "new password":
        		formElement = account_details_form.findElement(By.id("passWord"));
        		break;
        	case "re-enter new password":
        		formElement = account_details_form.findElement(By.id("passWordConf"));
        		break;
        	case "save button":
        		formElement = account_details_form.findElement(By.name("save_changes"));
        		break;
        	default:
                logger.debug("Unable to find element {} in myDetail form ", fieldLabel);
                throw new WebDriverException("Unable to find element in myDetail form "+fieldLabel);
        }
        
        return formElement;
    }
}
