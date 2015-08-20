package com.jcrew.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.util.PropertyReader;

public class LoginPage {

    @FindBy(id = "sidecarUser")
    private WebElement emailInput;

    @FindBy(id = "sidecarPassword")
    private WebElement passwordInput;

    @FindBy(className = "btn--signin")
    private WebElement signInButton;

    @FindBy(className = "js-invalid-msg")
    private WebElement invalidSignInMessage;
    
    @FindBy(id = "sidecarRemember")
    private WebElement keepMeSignedInCheckBox;
    
    @FindBy(id = "c-header__userpanelrecognized")
    private WebElement myAccount;
    
    @FindBy(className = "my_account_lefnav")
    private List<WebElement> leftNavigations;
    
    @FindBy(id = "main_inside")
    private WebElement welcomeName;
    
    @FindBy(id = "c-nav__userpanel")
    private WebElement myaccountRef;
    
   // WebElement myaccountlink = myaccountRef.findElement(By.linkText("MY ACCOUNT"));
    @FindBy(css = "#c-nav__userpanel > a")
    private WebElement myaccountlink;
    
    private final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void input_as_email(String email) {
        emailInput.sendKeys(email);
    }

    public void input_as_password(String password) {
        passwordInput.sendKeys(password);
    }

    public void click_sign_in_button() {
        signInButton.click();
    }

    public String getSignInErrorMessage() {
        return invalidSignInMessage.getText();
    }
    public void enter_valid_username_and_password() {
        PropertyReader reader = PropertyReader.getPropertyReader();
        input_as_email(reader.readProperty("checkout.signed.in.username"));
        input_as_password(reader.readProperty("checkout.signed.in.password"));    
    }
    
    public boolean isCheckBoxEnabled()  {
        return keepMeSignedInCheckBox.isEnabled();
    }
    
    public boolean isInAccountPage() {
        return myAccount.isDisplayed();
        
    }
    public String isMyDetailsPresent() {
        return leftNavigations.get(0).getText();
         
     }
     
    public String isEmailPreferencePresent() {
        return leftNavigations.get(1).getText();
        
    } 
    
    
    public String isCatalogPreferencePresent() {
        return leftNavigations.get(2).getText();
        
    }
    
    public String isPaymentMethodPresent() {
        return leftNavigations.get(3).getText();
        
    }
    
    public String isGiftCardBalancePresent() {
        return leftNavigations.get(4).getText();
        
    }
    
    public String isAddressBookPresent() {
        return leftNavigations.get(5).getText();
        
    }
    
    public String isOrderHistoryPresent() {
        return leftNavigations.get(6).getText();
        
    }
    
    public String isUserNamePresent() {
        String welcomeMessage = welcomeName.findElement(By.tagName("b")).getText();
        logger.info("verifying welcome message {}",welcomeMessage);
        return welcomeMessage;
    }
    
    public void printNavigationList() {
        logger.info("entering");
        for(WebElement leftNavigation:leftNavigations)
        {
            logger.info("verifying left navigation {}",leftNavigation.getText());
        }
        logger.info("exiting");
    }
    
    public boolean isMyAccountLinkPresent() {
      // WebElement myaccountlink = myaccountRef.findElement(By.linkText("MY ACCOUNT"));
      // WebElement myaccountlink = driver.findElement(By.cssSelector("#c-nav__userpanel > a"));
        logger.info("verify account link{}",myaccountlink.getText());
        
                return myaccountlink.isDisplayed();
    }
    
    public boolean isMyAccountInDesktop() {
        
            
            WebElement diffmyaccount = new WebDriverWait(driver, 10).
                    until(ExpectedConditions.elementToBeClickable(By.id("c-header__userpanelrecognized")));
            
            diffmyaccount.click();
            
            return diffmyaccount.findElement(By.xpath("//*[@id='c-nav__userpanel']/dl/dd[2]/a")).isDisplayed();
            
        }
        
    
    
    public void close_browser() {
        
        driver.close();
        
      
    } 
    
    public void open_browser() {
       WebDriver driver1 = new ChromeDriver();
        driver.get("https://www.sidecar-brn-qa.jcrew.com/");
    }
    
    public void click_my_account_link() {
       // WebElement myaccountlink = myaccountRef.findElement(By.linkText("MY ACCOUNT"));
      //  WebElement myaccountlink = driver.findElement(By.cssSelector("#c-nav__userpanel > a"));
        myaccountlink.click();
    }
    
    public void disable_checkbox() {
        keepMeSignedInCheckBox.click();
    }
    
    public void navigate_to_homepage() {
        driver.navigate().to("https://www.sidecar-brn-qa.jcrew.com");
    }
        
    
        
}

