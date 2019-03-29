package com.jcrew.page.checkout;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;



/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutConfirmation extends Checkout {
	static String orderNum =null;
    @FindBy(id = "confirmation-number")
    private WebElement confirmation_number;
    @FindBy(xpath = "//div[@class='footer__country-context']")
    private WebElement footer;
    @FindBy(id = "gifting-details")
    private WebElement giftDetails;
    public CheckoutConfirmation() {
        this.orderNum=orderNum;
     }
    public CheckoutConfirmation(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(confirmation_number));
    }
    public static String getOrderNum() {
		return orderNum;
	}
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");
        logger.debug("class: {}", getBodyAttribute("class"));

        return bodyId.equals("confirmation");
    }

	public String getConfirmationCode() throws Exception {
		WebElement numberElement = confirmation_number.findElement(By.className("order-number"));
		 orderNum = numberElement.getText().trim();
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(screenshot.getImage(),"PNG",new File(System.getProperty("user.dir")+"\\Screenshots\\"+orderNum+".png"));
		
		return numberElement.getText().trim();
	}

    public String getGitfMessage() {
        WebElement options = giftDetails.findElement(By.className("options-receiptmsg"));
        WebElement message = options.findElement(By.tagName("pre"));

        return message.getText();
    }
    
    public void handleBizratePopup(){
    	try {
    		List<WebElement> bizratePopupClose = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("brdialog-close")));
        	if(bizratePopupClose.size()>0){
        		bizratePopupClose.get(0).click();
        		logger.debug("Bizrate popup is closed...");
        	}
    	}catch (Exception e) {
			
		}
    	
    }

    public boolean isOrderConfirmationPage() {
        List<WebElement> confirmation = driver.findElements(By.id("confirmation-number"));
        return confirmation.size() > 0;
    }
}
