package com.jcrew.page.account;

import com.jcrew.page.header.HeaderWrap;
import com.jcrew.pojo.Country;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class FactoryMyAccount extends Account implements IMyAccount {

    @FindBy(className = "accountMainContainer")
    private WebElement main_content;
    @FindBy(className = "mainHomeContainer")
    private WebElement main_inside;
    @FindBy(className = "leftNavContainer")
    private WebElement navigation;

    public FactoryMyAccount(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(main_content));

        HeaderWrap header = new HeaderWrap(driver);
    }


    public boolean isMyAccountMainPage() {
        WebElement homecopy = main_inside.findElement(By.xpath(".//div[@class='accountWelcome']"));

        return homecopy.isDisplayed();
    }

    public void clickInMenuOption(String menuOption) {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public boolean isOrderHistoryPage() {
    	wait.until(ExpectedConditions.visibilityOf(main_content));
        
        WebElement myOrdersTitle = main_content.findElement(By.className("orderHistoryTitle"));
        boolean expectedContent = "order history".equalsIgnoreCase(myOrdersTitle.getText());

        Country country = stateHolder.get("context");
        boolean expectedURL = Util.countryContextURLCompliance(driver, country, "account/reg_user_order_history.jsp?");

        return expectedContent & expectedURL;
    }

    public void click_reward_link(String link) {
        throw new WebDriverException("Method not implemented for Factory");

    }

    public void click_menu_link(String link) {
        String url = driver.getCurrentUrl();
        WebElement linkElement = navigation.findElement(By.xpath(".//div[@class='leftNavItem']/a[contains(.,'" + link + "')]"));
        linkElement.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }


    private WebElement getMenuLink(String link) {
        throw new WebDriverException("Method not implemented for Factory");
    }

    public boolean isInMenuLinkPage(String page) {
    	return wait.until(ExpectedConditions.urlContains(page));
    }

    public boolean isMenuLinkPresent(String link) {
        throw new WebDriverException("Method not implemented for Factory");
    }

}
