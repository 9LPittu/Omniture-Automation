package com.jcrew.page.account;

import com.jcrew.page.header.HeaderWrap;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.Util;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
@SuppressWarnings("unused")
public class JcrewMyAccount extends Account implements IMyAccount {

    @FindBy(id = "main_cont")
    private WebElement main_content;
    @FindBy(id = "main_inside")
    private WebElement main_inside;
    @FindBy(xpath = "//DIV[@class='account__my-details--header'][text()='Change Password']")
   	private WebElement changePassword;
  
    @FindBy(xpath = "//BUTTON[text()='save details']")
   	private WebElement SaveDetails;
    @FindBy(id = "containerBorderLeft")
    private WebElement leftContainer;
    @FindBy(id = "main_cont_hist")
    private WebElement main_content_history;
    @FindBy(xpath = "//span[contains(text(),'Your information has been updated.')]")
    private WebElement successMessage;
    String password;
    String emailID;
    public JcrewMyAccount(WebDriver driver) {
        super(driver);
       // wait.until(ExpectedConditions.visibilityOf(main_content));

        HeaderWrap header = new HeaderWrap(driver);
    }


    public boolean isMyAccountMainPage() {
        WebElement myAccountBanner = main_content.findElement(By.tagName("h2"));
        String bannerText = myAccountBanner.getText();
        WebElement homecopy = main_inside.findElement(By.xpath(".//td[@class='homecopysm']"));

        return "MY ACCOUNT".equalsIgnoreCase(bannerText) && homecopy.isDisplayed();
    }

    public void clickInMenuOption(String menuOption) {
        By optionLocator = By.xpath(".//a[@class='my_account_lefnav' and contains(text(),'" + menuOption + "')]");
        WebElement option = main_content.findElement(optionLocator);
        option.click();
    }

    public boolean isOrderHistoryPage() {
        wait.until(ExpectedConditions.visibilityOf(main_content_history));
        WebElement myAccountBanner = main_content_history.findElement(By.tagName("h2"));
        String bannerText = myAccountBanner.getText();
        wait.until(ExpectedConditions.visibilityOf(leftContainer));
        WebElement myOrdersTitle = leftContainer.findElement(By.className("myaccount_list"));

        boolean expectedContent = "MY ACCOUNT".equalsIgnoreCase(bannerText) &&
                "My Orders".equalsIgnoreCase(myOrdersTitle.getText());

        Country country = stateHolder.get("context");
        boolean expectedURL = Util.countryContextURLCompliance(driver, country, "account/reg_user_order_history.jsp?");

        return expectedContent & expectedURL;
    }

    public void click_reward_link(String link) {
        WebElement menu = null;
        User signedInUser = stateHolder.get("signedUser");
        Country c = stateHolder.get("context");
        boolean rewardLinkShouldExists = ((signedInUser.getUserCategory().equalsIgnoreCase(User.CAT_LOYALTY)) && "us".equalsIgnoreCase(c.getCountry()));
        if (rewardLinkShouldExists) {
            menu = getMenuLink(link);
            wait.until(ExpectedConditions.elementToBeClickable(menu));
            Util.clickWithStaleRetry(menu);
        }

    }

    public void click_menu_link(String link) {
        WebElement menu = null;

        Country c = stateHolder.get("context");


        // to validate the my account page left nav links
        //US: Gift card balance, Catalog Preferences,My Details, Email Preferences, Payment Methods, Address Book, Order History, Wish list & Sign Out
        //CANADA: All the above except Gift card balance will be there
        //All the other countries: Gift card Balance and Catalog Preferences will not be present


        switch (c.getCountry()) {
            case "us":
                menu = getMenuLink(link);
                break;
            case "ca":
                if (!link.equalsIgnoreCase("GIFT CARD BALANCE")) {
                    menu = getMenuLink(link);
                }
                break;
            default:
                if (!link.equalsIgnoreCase("GIFT CARD BALANCE") && !link.equalsIgnoreCase("CATALOG PREFERENCES")) {
                    menu = getMenuLink(link);
                }
                break;
        }

        if (menu != null) {
            wait.until(ExpectedConditions.elementToBeClickable(menu));
            Util.clickWithStaleRetry(menu);
        }
    }


    private WebElement getMenuLink(String link) {
        Util.waitForPageFullyLoaded(driver);
        Country c = stateHolder.get("context");
        logger.debug(c.getCountry());
       // wait.until(ExpectedConditions.visibilityOf(main_inside));
       // return main_inside.findElement(By.linkText(link));
		return null;
    }

    public boolean isInMenuLinkPage(String page) {
        Country c = stateHolder.get("context");
        User signedInUser = stateHolder.get("signedUser");
        // to validate the my account page left nav links
        //US: Gift card balance, Catalog Preferences,My Details, Email Preferences, Payment Methods, Address Book, Order History, Wish list & Sign Out
        //CANADA: All the above except Gift card balance will be present
        //All the other countries: Gift card Balance and Catalog Preferences will not be present. everything else will be there
        boolean ifReward = page.contains("rewards");
        boolean testRewardVisible = true;
        if (ifReward) {
            testRewardVisible = ((signedInUser.getUserCategory().equalsIgnoreCase(User.CAT_LOYALTY)) && "us".equalsIgnoreCase(c.getCountry()) && ifReward);
        }

        boolean forOtherCountries = !(page.contains("giftcard") || page.contains("catalog_preferences"));

        if ((("ca".equals(c.getCountry()) && !(page.contains("giftcard"))) || "us".equals(c.getCountry()) || forOtherCountries) && testRewardVisible)
            return wait.until(ExpectedConditions.urlContains(page));
        else {
            logger.info("expected no " + page + " for " + c.getCountry());
            return true;
        }

    }

    public boolean isMenuLinkPresent(String link) {
        Util.waitForPageFullyLoaded(driver);
        wait.until(ExpectedConditions.visibilityOf(main_inside));
        try {
            WebElement menuLink = driver.findElement(By.xpath("//a[@class='my_account_lefnav' and contains(" + Util.xpathGetTextLower + ",'" + link.toLowerCase() + "')]"));
            return (menuLink.isDisplayed());
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public boolean verifySuccessMessage() {
    	successMessage = wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.isDisplayed();
    }
    @Override
	public void scrollIntoElement() {
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream("properties/e2e.properties");
			 Properties properties = new Properties();
			 properties.load(inputFile);
			 password=  properties.getProperty("password");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", changePassword);
			 WebElement currentPasswordTxt = driver.findElement(By.xpath("//INPUT[@id='my-details-form__old-password']"));
			 currentPasswordTxt.clear();
			 currentPasswordTxt.sendKeys(password);
			 WebElement newPasswordTxt = driver.findElement(By.xpath("//INPUT[@id='my-details-form__new-password']"));
			 newPasswordTxt.clear();
			 newPasswordTxt.sendKeys(password);
			 WebElement confirmPasswordTxt = driver.findElement(By.xpath("//INPUT[@id='my-details-form__confirm-password']"));
			 confirmPasswordTxt.clear();
			 confirmPasswordTxt.sendKeys(password);
			 try {
				Thread.sleep(5000);
				WebElement saveDetailsbtn = driver.findElement(By.xpath("//BUTTON[@class='my-details-form__btn-submit'][text()='save details']"));
				 saveDetailsbtn.click();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	}

    @Override
	public void updateUserID() {
		/*FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream("properties/e2e.properties");
			 Properties properties = new Properties();
			 properties.load(inputFile);
			 emailID=  properties.getProperty("emailID");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		int x = (int) Math.floor((Math.random() * 99));
		String userName = "testautousera" + x+"@gmail.com";
		System.out.println(userName);
		WebElement updateUserdIDTxt = driver.findElement(By.xpath("//INPUT[@id='my-details-form__email']"));
		updateUserdIDTxt.clear();
		updateUserdIDTxt.sendKeys(userName);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", changePassword);
			 try {
				Thread.sleep(5000);
				WebElement saveDetailsbtn = driver.findElement(By.xpath("//BUTTON[text()='save details']"));
				 saveDetailsbtn.click();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	}
}
