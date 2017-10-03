package com.jcrew.page.header;

import com.jcrew.page.PageObject;
import com.jcrew.pojo.Product;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrap extends PageObject {
	public final StateHolder stateHolder = StateHolder.getInstance();

	@FindBy(id = "c-header__userpanel")
	private WebElement sign_in;
	@FindBy(id = "c-header__userpanelrecognized")
	private WebElement myAccount;
	@FindBy(id = "c-header__userpanel")
	private WebElement userPanel;

	private WebElement dropdown;

	public HeaderWrap(WebDriver driver) {
        super(driver);
        GlobalPromo promo = new GlobalPromo(driver);
	}

	public void clickSignIn() {
		int cntr = 0;
		do{
			try{
				Util.waitLoadingBar(driver);
				Util.waitForPageFullyLoaded(driver);
				Util.createWebDriverWait(driver, Util.DEFAULT_TIMEOUT/3).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(sign_in)));
				Util.createWebDriverWait(driver, Util.DEFAULT_TIMEOUT/3).until(ExpectedConditions.visibilityOf(sign_in));
				WebElement signInLink = sign_in.findElement(By.tagName("a"));
				signInLink.click();
				Util.createWebDriverWait(driver, Util.DEFAULT_TIMEOUT/3).until(ExpectedConditions.urlContains("/r/login"));
				break;
			}
			catch(StaleElementReferenceException sere){
				cntr++;
			}
			catch(TimeoutException toe){
				cntr++;
			}			
		}while(cntr<=2);		
	}

    public void myAccount(){
        wait.until(ExpectedConditions.visibilityOf(myAccount));
        //myAccount.click();
        Util.scrollPage(driver, "UP");
        Util.wait(2000);
        myAccount.click();
    }
    
    public boolean isMyAccountDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(myAccount));
        return myAccount.isDisplayed();
    }
    
    public boolean isMyAccountDropdownDisplayed(){
        dropdown = userPanel.findElement(By.tagName("dl"));
        return dropdown.isDisplayed();
    }

	public void hoverOverIcon(String icon) {
		if ("my account".equalsIgnoreCase(icon)) {
			wait.until(ExpectedConditions.visibilityOf(myAccount));
			hoverAction.moveToElement(myAccount);
			hoverAction.perform();

		} else if ("logo".equalsIgnoreCase(icon)) {
            HeaderLogo logo = new HeaderLogo(driver);
            logo.hoverLogo();

		} else if ("gender landing".equalsIgnoreCase(icon)) {
            TopNav topNav = new TopNav(driver);
            topNav.hoverCategory("men");

		} else if ("bag".equalsIgnoreCase(icon)) {
			HeaderBag bag = new HeaderBag(driver);
			bag.hoverBag();
		}
	}

	public String getWelcomeMessage() {
		dropdown = userPanel.findElement(By.tagName("dl"));
		WebElement welcomeRow = dropdown.findElement(
				By.xpath(".//dd[@class='c-nav__userpanel--welcomeuser']"));
		String message = welcomeRow.getText();

		if (message.isEmpty()) {
			hoverOverIcon("my account");
			message = welcomeRow.getText();
		}

		hoverOverIcon("logo");

		return message;
	}

	public void goToMyDetailsDropDownMenu(String option) {
		String url = driver.getCurrentUrl();

		hoverOverIcon("my account");
		Util.wait(1000);
		dropdown = wait.until(ExpectedConditions.visibilityOf(userPanel.findElement(By.tagName("dl"))));

		WebElement optionElement = dropdown.findElement(
				By.xpath(".//a[" + Util.xpathGetTextLower + "='" + option.toLowerCase() + "']"));
		optionElement.click();

		Util.waitLoadingBar(driver);

		if ("sign out".equalsIgnoreCase(option)) {
			List<Product> bag = stateHolder.getList("toBag");
			stateHolder.put("userBag", bag);
			stateHolder.remove("toBag");

			wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
		}
	}

	public boolean isSignInVisible() {
		boolean result = false;
		wait.until(ExpectedConditions.visibilityOf(sign_in));
		List<WebElement> signInLink = sign_in.findElements(By.tagName("a"));

		if (signInLink.size() == 1)
			result = true;

		return result;
	}

}
