package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
@SuppressWarnings("unused")
public class MiniBag {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MiniBag.class);
    private final WebDriverWait wait;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String ITEM_NAME_CLASS = "minibag-item__body--title";
    private final String ITEM_PRICE_CLASS = "minibag-item__body--price";
    private final String ITEM_COLOR_CLASS = "minibag-item__body--color";
    private final String ITEM_SIZE_CLASS = "minibag-item__body--size";

    @FindBy(id = "c-header__minibag")
    private WebElement minibag;
    @FindBy(id = "btn__checkout")
    private WebElement checkoutButton;

    public MiniBag(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
    }

    public int getItemsNumber() {
        List<WebElement> items = minibag.findElements(By.tagName("li"));

        return items.size();
    }

    public boolean showsMoreItems() {
        List<WebElement> see_more = minibag.findElements(By.xpath(".//div[@class='minibag__see-more']"));

        return see_more.size() == 1;
    }

    public Product getItem(int itemNumber) {
        List<WebElement> items = minibag.findElements(By.xpath(".//ul/li"));
        WebElement item = items.get(itemNumber);

        Product product = new Product();
        WebElement information;

        information = item.findElement(By.className(ITEM_NAME_CLASS));
        String name = information.getText();
        name = name.replace("Pre-order ", "");
        product.setName(name);
        information = item.findElement(By.className(ITEM_COLOR_CLASS));
        product.setColor(information.getText());
        information = item.findElement(By.className(ITEM_SIZE_CLASS));
        product.setSize(information.getText());

        information = item.findElement(By.className(ITEM_PRICE_CLASS));
        String price = information.getText().trim();

        if (price.contains("x")) {
            //price line contains quantity information
            String line[] = price.split("x");
            product.setQuantity(line[1]);
            product.setPrice(line[0]);
        } else {
            product.setPrice(price);
            product.setQuantity("1");
        }

        return product;
    }

    public boolean messageAndButtonToCart(){
        boolean result = false;
        String hrefButton = checkoutButton.getAttribute("href");
        List<WebElement> see_more = minibag.findElements(By.xpath(".//div[@class='minibag__see-more']"));

        if(see_more.size() > 0){
            WebElement message = see_more.get(0);
            WebElement messageLink = message.findElement(By.tagName("a"));
            String hrefLink = messageLink.getAttribute("href");
            result = hrefButton.equals(hrefLink);
        }

        return result;
    }

    public void clickOnItem(int itemNumber) {
        List<WebElement> items = minibag.findElements(By.xpath(".//ul/li"));
        WebElement item = items.get(itemNumber);
        WebElement itemImage = item.findElement(By.tagName("img"));

        wait.until(ExpectedConditions.elementToBeClickable(itemImage));
        itemImage.click();
    }

    public String getMiniCartSubtotal() {
        WebElement subtotalElement = minibag.findElement(By.className("minibag__subtotal"));
        String subtotalText = subtotalElement.getText();
        subtotalText = subtotalText.replaceAll("[^0-9.]", "");

        return subtotalText;
    }

    public boolean isMiniBagVisible() {
        return minibag.isDisplayed();
    }

    public boolean isCorrectCurrencyDisplayedOnMinibag() {

        Country c = (Country) stateHolder.get("context");

        List<WebElement> itemprices = wait.until(ExpectedConditions.visibilityOfAllElements(minibag.findElements(By.className("minibag-item__body--price"))));
        WebElement subTotalPrice = wait.until(ExpectedConditions.visibilityOf(minibag.findElement(By.className("minibag__subtotal"))));
        return CurrencyChecker.validatePrices(itemprices, c) && CurrencyChecker.validatePrice(subTotalPrice, c);


    }

}
