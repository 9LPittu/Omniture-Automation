package com.madewell.page;

import com.madewell.pojo.ShippingMethod;
import com.madewell.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 5/6/16.
 */
public class CheckoutShippingOptions extends Checkout {

    @FindBy(id = "frmSelectShippingMethod")
    private WebElement shippingMethodForm;

    public CheckoutShippingOptions(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(shippingMethodForm));
    }

    @Override
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("method");
    }

    public List<ShippingMethod> getShippingMethods() {
        List<WebElement> methods = shippingMethodForm.findElements(By.className("form-shipmethod"));
        List<ShippingMethod> shippingMethods = new ArrayList<>();

        for (WebElement method : methods) {
            shippingMethods.add(getShippingMethod(method));
        }

        return shippingMethods;
    }

    public ShippingMethod getSelectedShippingMethod() {
        WebElement selectedLabel = shippingMethodForm.findElement(By.xpath(".//label[@class='form-label radio-checked']"));
        WebElement selectedMethod = selectedLabel.findElement(By.xpath(".//ancestor::div[contains(@class,'form-shipmethod')]"));

        return getShippingMethod(selectedMethod);
    }

    private ShippingMethod getShippingMethod(WebElement method) {
        WebElement methodElement = method.findElement(By.className("method-group"));
        String methodText = methodElement.getText().trim();

        WebElement priceElement = method.findElement(By.className("method-price"));
        String priceText = priceElement.getText().trim();

        List<WebElement> textElement = method.findElements(By.className("method-text"));
        String text = "";

        if (textElement.size() > 0) {
            text = textElement.get(0).getText();
        }

        String methodType = methodText.replace(priceText, "").replace(text, "").trim();

        return new ShippingMethod(methodType, priceText, text);
    }

    public void selectShippingMethod() {
        List<WebElement> methods = shippingMethodForm.findElements(By.className("form-shipmethod"));
        int random = Util.randomIndex(methods.size());

        WebElement method = methods.get(random);
        WebElement label = method.findElement(By.tagName("label"));
        String labelClass = label.getAttribute("class");

        logger.debug("Selected shipping method: {}", label.getText());

        if (!labelClass.contains("radio-checked")) {
            WebElement radio = method.findElement(By.className("input-radio"));
            radio.click();
        } else {
            logger.debug("Selected method is already selected");
        }

        ShippingMethod shippingMethod = getShippingMethod(method);

        stateHolder.put("selectedShippingMethod", shippingMethod.getMethod());
    }

    public void continueCheckout() {
        nextStep(shippingMethodForm);
    }

    public boolean hasGiftOption() {
        WebElement giftOption = shippingMethodForm.findElement(By.className("form-section-gift-options"));

        return giftOption.isDisplayed();
    }

    public void addGiftOption() {
        WebElement add = shippingMethodForm.findElement(By.id("includesGifts"));
        add.click();
    }

    public void addGiftMessage(String message) {
        WebElement textArea = shippingMethodForm.findElement(By.id("gift-receipt-msg0"));
        textArea.sendKeys(message);
    }
    
    public String getGiftReceiptInfoMessage(){
    	WebElement giftReceiptInfoMessage = shippingMethodForm.findElement(By.className("gift-receipt-info"));
    	return giftReceiptInfoMessage.getText().trim();
    }
}
