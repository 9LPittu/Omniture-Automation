package com.jcrew.page;

import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Footer {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Footer.class);
    private final StateHolder stateHolder = StateHolder.getInstance();
    
    @FindBy(className = "js-footer__row__wrap--main")
    private WebElement footerWrapMain;
    
    @FindBy(className = "footer__row--bottom")
    private  WebElement footerRowBottom;
    
    @FindBy(className = "footer__row--top")
    private  WebElement footerRowTop;
    
    @FindBy(className = "js-footer__menu")
    private List<WebElement> subLinks;
    
    @FindBy(xpath="//legend[@class='footer__header' and text()='like being first?']/following-sibling::input[@name='subscribeEmail']")
    private WebElement emailField;
      
    @FindBy(className="footer__signup__button")
    private WebElement footerSignUpButton;
    
    @FindBy(className="js-footer__signup__copy")
    private WebElement footerSignUpMessage;
    
    @FindBy(className="footer__country-context")
    private WebElement shipToSectionInFooter;
    
    @FindBy(xpath=".//div[@class='footer__country-context']/descendant::span[@class='footer__country-context__country']")
    private WebElement countryNameInFooter;
    
    @FindBy(xpath=".//div[@class='footer__country-context']/descendant::a[@class='footer__country-context__link']")
    private WebElement changeLinkInFooter;
     
    public Footer(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isFooterLinkPresent(String footerLink) {
        return getFooterLinkElement(footerLink).isDisplayed();

    }

    private WebElement getFooterLinkElement(String footerLink) {
        try {
            return footerWrapMain.findElement(By.xpath("//h6[text()='" + footerLink + "']"));
        } catch (StaleElementReferenceException e) {
            logger.debug("Stale Element Exception was thrown, retry to click on footer element {}", footerLink);
            return getFooterLinkElement(footerLink);
        }
    }

    public void click_on(String footerLink) {
        getFooterLinkElement(footerLink).click();
    }


    public String getFooterSubText(String footerLink) {
        WebElement listOfSubElements = getListOfSubElementsForFooterLink(footerLink);
        WebElement footerSubTextElement = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(listOfSubElements.findElement(By.className("footer__item__text"))));
        return footerSubTextElement.getText();
    }

    private WebElement getListOfSubElementsForFooterLink(String footerLink) {
        WebElement footerLinkElement = getFooterLinkElement(footerLink);
        return footerLinkElement.findElement(By.xpath("following-sibling::ul"));
    }

    public void click_sublink_from(String footerSubLink, String footerLink) {
        WebElement listOfSubElements = getListOfSubElementsForFooterLink(footerLink);
        WebElement footerSublink = listOfSubElements.findElement(By.linkText(footerSubLink));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(footerSublink));
        footerSublink.click();
    }

    public boolean isSubLinkDisplayed(String sublink) {
        boolean subLinkDisplayed = false;
        for(WebElement subLink: subLinks) {
            subLinkDisplayed = subLink.findElement(By.xpath("//a[text()='" + sublink + "']")).isDisplayed();
        }
            return subLinkDisplayed;
    }


    public String getFooterHeaderLegend() {
        return footerWrapMain.findElement(By.tagName("legend")).getText();
    }

    public boolean isTopHeaderVisible(String text) {
        return footerRowTop.findElement(By.xpath("//h6[text()='" + text + "']")).isDisplayed();
    }

    public boolean isIconAndTextDisplayed(String icon) {
        List<WebElement> contactUsIconsList = footerRowTop.findElements(By.className("footer__help__menu"));
        boolean iconDisplayed = false;
        for(WebElement contactUsIcon: contactUsIconsList) {
            //Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(contactUsIcon.findElement(By.cssSelector("a[href*='"+icon+"']"))));
            System.out.println(contactUsIcon.getText());
             iconDisplayed =contactUsIcon.findElement(By.className("footer__help__item--"+icon)).isDisplayed();
        }
        return iconDisplayed;
    }

    public boolean isSocialIconDisplayed(String socialIcon) {
        List<WebElement> socialNetworkIconsList = footerWrapMain.findElements(By.className("footer__social__menu"));
        boolean iconDisplayed = false;
        for(WebElement socialNetworkIcon: socialNetworkIconsList) {
            //Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(contactUsIcon.findElement(By.cssSelector("a[href*='"+icon+"']"))));
            iconDisplayed =socialNetworkIcon.findElement(By.className("footer-"+socialIcon)).isDisplayed();
        }
        return iconDisplayed;
    }

    public boolean isEmailFieldDisplayed()  {
        return footerWrapMain.findElement(By.tagName("input")).isDisplayed();
    }


    public void click_bottom_link(String bottomLink) {
        footerRowBottom.findElement(By.linkText(bottomLink)).click();
    }
    
    public boolean isEmailDisplayedUnderLikeBeingFirstSection(){    	
    	return emailField.isDisplayed();
    }
    
    public boolean isEmailFieldMatchesWithDefaultText(String defaultText){
    	String emailFieldDefaultText = emailField.getAttribute("placeholder").trim();
    	return emailFieldDefaultText.equalsIgnoreCase(defaultText);
    }
    
    public void enterEmailAddressInFooterEmailField(String emailAddress){
    	emailField.clear();
    	emailField.sendKeys(emailAddress);
    }
    
    public void clickSignUpButtonInFooter(){
    	footerSignUpButton.click();
    }
    
    public boolean isMessageDisplayedCorrectlyDuringFooterSignUp(String message) throws InterruptedException{    	
    	Thread.sleep(500);
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(footerSignUpMessage));		
		String actualMessage = footerSignUpMessage.getText().toLowerCase();
		actualMessage = actualMessage.replace("\n", "");
		System.out.println("Message displayed:" + actualMessage);	
		return actualMessage.contains(message.toLowerCase());    	
    }
    
    public boolean isShipToSectionDisplayed(){
    	return shipToSectionInFooter.isDisplayed();
    }
    
    public boolean isCountryNameDisplayedInFooter(){
    	return countryNameInFooter.isDisplayed();
    }
    
    public boolean isChangeLinkDisplayedInFooter(){
    	return changeLinkInFooter.isDisplayed();
    }
    
    public void clickChangeLinkInFooter(){
    	changeLinkInFooter.click();
    }
    
    public void selectCountry(String country){
    	List<WebElement> countryNames = driver.findElements(By.className("submitCountryChange"));
    	for(int i=0;i<countryNames.size();i++){
    		WebElement countryName = countryNames.get(i);
    		if(countryName.getText().equalsIgnoreCase(country)){
    			countryName.click();
    			break;
    		}
    	}
    }
    
    public boolean isChangedCountryNameDsiplayedInFooter(String country){
    	Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(countryNameInFooter, country.toUpperCase()));
    	String currentCountryName = countryNameInFooter.getText().trim();
    	return currentCountryName.equalsIgnoreCase(country);
    }
}