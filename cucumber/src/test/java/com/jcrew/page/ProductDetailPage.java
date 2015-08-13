package com.jcrew.page;

import org.apache.commons.lang3.StringUtils;
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

public class ProductDetailPage {

    private final WebDriver driver;

    private Logger logger = LoggerFactory.getLogger(ProductDetailPage.class);

    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBag;

    @FindBy(id = "btn__wishlist")
    private WebElement wishList;

    @FindBy(className = "variations-list")
    private WebElement variationsListSection;

    @FindBy(className = "c-product__colors")
    private WebElement productColorsSection;

    @FindBy(id = "c-product__sizes")
    private WebElement productSizesSection;

    @FindBy(className = "product__name")
    private WebElement productName;

    @FindBy(className = "primary-nav__item--bag")
    private WebElement bagContainer;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(css = ".primary-nav__item--bag > .primary-nav__link")
    private WebElement itemBagLink;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public boolean isProductDetailPage() {
        boolean isProductDetailPage = productName.isDisplayed() && StringUtils.isNotBlank(productName.getText());
        return isProductDetailPage && footer.isDisplayed();
    }

    public void select_variation() {
        List<WebElement> variations = variationsListSection.findElements(By.tagName("input"));

        WebElement variation = variations.get(0);

        if (variation.isSelected()) {
            logger.debug("Variation is already selected");

        } else {

            variation.click();
        }

    }

    public void select_color() {
        List<WebElement> colors = productColorsSection.findElements(By.className("colors-list__item"));

        WebElement color = colors.get(0);

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(color));

        color.click();

    }

    public void select_size() {
        List<WebElement> sizes = productSizesSection.findElements(By.className("sizes-list__item"));
        WebElement size = sizes.get(0);

        size.click();

        if (size.getAttribute("class").contains("is-selected")) {
            logger.debug("Size has been selected {}", size.findElement(By.tagName("span")).getText());
        }
    }

    public boolean isWishlistButtonPresent() {
        return wishList.isDisplayed();
    }

    public void click_add_to_cart() {
        addToBag.click();
    }

    public int getNumberOfItemsInBag() {
        WebElement bagSize = bagContainer.findElement(By.className("js-cart-size"));
        String bagSizeStr = bagSize.getText();
        logger.debug("Bag Size is {}", bagSizeStr);
        String stringSize = bagSizeStr.replace("(", "").replace(")", "").trim();
        return Integer.parseInt(stringSize);
    }

    public String getMinicartMessage() {
        final WebElement miniCart = new WebDriverWait(driver, 10).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header__cart--details > p")));
        final String confirmationMessage = miniCart.getAttribute("innerHTML");
        logger.debug("Confirmation message is {} ", confirmationMessage);
        return confirmationMessage;
    }

    public void click_item_bag() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(itemBagLink)).click();
    }
}
