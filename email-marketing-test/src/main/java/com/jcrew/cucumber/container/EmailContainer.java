package com.jcrew.cucumber.container;

import com.jcrew.helper.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmailContainer {

    @FindBy(how = How.XPATH, using = "//html//table//a[contains(@style,'text-decoration:underline')]")
    public WebElement preHeader;

    @FindBy(how = How.XPATH, using = "//*[contains(@alt,'http')]")
    public List<WebElement> links;

    @FindBy(how = How.XPATH, using = "//img[contains(@src,'jpg')]")
    public List<WebElement> emailImages;

    @FindBy(how = How.XPATH, using = "//footer//a[text()='change']")
    public WebElement countrySelection;

    @FindBy(how = How.XPATH, using = "//span[text()='United States']")
    public WebElement us;

    public List<WebElement> getEmailImagesFromAddLink() {
        List<WebElement> emailImagesFromAddLink = null;
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            emailImagesFromAddLink = BrowserDriver.getCurrentDriver().findElements(By.xpath("//img[contains(@src,'http')]"));
        } catch (Exception e) {
        }
        BrowserDriver.setImplicitWaitToDefault();
        return emailImagesFromAddLink;
    }

    public WebElement getEmailBrowserLink() {
        WebElement emailBrowserLink = null;
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            emailBrowserLink = BrowserDriver.getCurrentDriver().findElement(By.xpath("//*[contains(text(),'web browser')]/parent::a[contains(@href,'http')]"));
        } catch (Exception e) {
            try {
                emailBrowserLink = BrowserDriver.getCurrentDriver().findElement(By.xpath("//*[contains(text(),'web browser')]/../*[ contains(@href,'http')]"));
            } catch (Exception ee) {
            }
        }
        BrowserDriver.setImplicitWaitToDefault();
        return emailBrowserLink;
    }

    public WebElement disclaimerCopy() {
        WebElement disclaimerCopy;
        try {
            disclaimerCopy = BrowserDriver.getCurrentDriver().findElement(By.xpath("(//tbody)[last()-1]//tr//*[contains(@style,'font-family:')]"));
        } catch (Exception e) {
            disclaimerCopy = BrowserDriver.getCurrentDriver().findElement(By.xpath("(//tbody)[last()]//tr//*[contains(@style,'font-family:')]"));
        }
        BrowserDriver.setImplicitWaitToDefault();
        return disclaimerCopy;
    }
}
