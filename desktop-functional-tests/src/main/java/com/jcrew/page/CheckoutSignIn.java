package com.jcrew.page;

import com.jcrew.pojo.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutSignIn extends Checkout {

    @FindBy(id = "userSignIn")
    private WebElement signInForm;
    
    @FindBy(id = "loginUser")
    private WebElement loginUser;
    
    @FindBy(id = "loginPassword")
    private WebElement loginPassword;
    
    @FindBy(id = "frmGuestCheckOut")
    private WebElement guestCheckOutForm;

    public final String DEFAULT = User.DEFAULT;
    public final String NO_DEFAULT = User.NO_DEFAULT;
    public final String MULTIPLE = User.MULTIPLE;
    public final String NO_DEFAULT_MULTIPLE = User.NO_DEFAULT_MULTIPLE;

    public CheckoutSignIn(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(signInForm));
    }

    @Override
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        boolean result = urlContains("/checkout2/shoppingbag.jsp");
        result &= bodyId.equals("signIn");

        return result;
    }

    public void signInAndCheckout(String type) {
        User user = User.getUser(type);
        enterLoginCredentials(user.getEmail(), user.getPassword());
    }

    public void clickGuestCheckOut() {
        String url = driver.getCurrentUrl();
        WebElement checkOutAsGuest = guestCheckOutForm.findElement(By.tagName("a"));

        wait.until(ExpectedConditions.elementToBeClickable(checkOutAsGuest));
        checkOutAsGuest.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }
    
    public void enterLoginCredentials(String emailAddress, String password){
    	
    	loginUser.sendKeys(emailAddress);
        loginPassword.sendKeys(password);

        String url = driver.getCurrentUrl();
        WebElement submit = signInForm.findElement(By.className("button-submit"));
        submit.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }
}
