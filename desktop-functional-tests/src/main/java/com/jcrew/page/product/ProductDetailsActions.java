package com.jcrew.page.product;

import com.jcrew.page.header.HeaderBag;
import com.jcrew.pojo.Product;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 3/23/17.
 */
public class ProductDetailsActions extends ProductDetails {

    @FindBy(id = "c-product__actions")
    private WebElement product_actions;

    private final String ADD_TO_BAG_ID = "btn__add-to-bag";
    private final String WISHSLIST_ID = "btn__wishlist";

    public ProductDetailsActions(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(product_actions));
    }

    public int getYCoordinate() {
        Point point = product_actions.getLocation();
        return point.getY();
    }

    public void addToBag() {
        stateHolder.addToList("toBag", getProduct());

        HeaderBag headerBag = new HeaderBag(driver);
        int itemsInBag = headerBag.getItemsInBag();

        logger.info("Adding to bag {}", getProductName());

        WebElement addToBagButton = product_actions.findElement(By.id(ADD_TO_BAG_ID));
        Util.scrollAndClick(driver, addToBagButton);

        //handle Ship Restriction Message
        List<WebElement> yesButton = driver.findElements(By.id("btn__yes"));
        if(yesButton.size() > 0) {
            yesButton.get(0).click();
        } else {
            logger.info("Ship restriction message not displayed");
        }

        //Verify minibag is displayed
        if(headerBag.isMiniBagDisplayed()) {
            logger.info("Mini cart is not displayed. Hence, checking item count in bag has increased");

            int itemCount = headerBag.getItemsInBag();
            if (itemCount <= itemsInBag) {
                Util.e2eErrorMessagesBuilder("Item " + getProductCode() + " is not added to bag");
                throw new WebDriverException("product not added to bag");
            }
        }

    }

    public boolean isWishlistDisplayed() {
        WebElement wishlistButton = product_actions.findElement(By.id(WISHSLIST_ID));
        return wishlistButton.isDisplayed();
    }

    public boolean isAddToBagDisplayed() {
        WebElement addToBagButton = product_actions.findElement(By.id(ADD_TO_BAG_ID));
        return addToBagButton.isDisplayed();
    }

    public boolean isUpdateBagDisplayed() {
        WebElement updateButton = product_actions.findElement(By.id(ADD_TO_BAG_ID));
        return wait.until(ExpectedConditions.textToBePresentInElement(updateButton, "UPDATE BAG"));
    }

    public void click_update_cart() {
        WebElement addToBagButton = product_actions.findElement(By.id(ADD_TO_BAG_ID));
        wait.until(ExpectedConditions.textToBePresentInElement(addToBagButton, "UPDATE BAG"));

        stateHolder.addToList("toBag", getProduct());
        stateHolder.addToList("editedItem", getProduct());

        HeaderBag headerBag = new HeaderBag(driver);
        int itemsInBag = headerBag.getItemsInBag();
        stateHolder.put("itemsInBag", itemsInBag);

        Util.clickWithStaleRetry(addToBagButton);
    }

    public boolean getIsBackordered() {
        String message = "";
        List<WebElement> messages = product_actions.findElements(By.className("product__message"));

        if (messages.size() > 0) {
            message = messages.get(0).getText().toLowerCase();
        }

        return message.contains("backordered");
    }

}
