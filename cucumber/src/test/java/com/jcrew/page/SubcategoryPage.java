package com.jcrew.page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubcategoryPage {


    private final WebDriver driver;

    @FindBy(css = "button.get-quickshop")
    private WebElement quickShopButton;

    @FindBy(xpath = ".//*[@id='sizes1']/div[not(contains(@class, 'unavailable'))][1]")
    private WebElement availableSize;

    @FindBy(className = "add-item")
    private WebElement addItem;

    @FindBy(className = "quickshop-close")
    private WebElement quickShopCloseButton;

    @FindBy(id = "globalHeaderShoppingBagBttn2")
    private WebElement shoppingBagLink;

    @FindBy(id = "qsLightBox")
    private WebElement quickShopModal;

    @FindBy(className = "product__grid")
    private WebElement productGrid;

    @FindBy(css = ".category__page-title > h2")
    private WebElement categoryPageTitle;

    @FindBy(className = "accordian__wrap")
    private WebElement accordionWrap;

    @FindAll(value = {
            @FindBy(xpath = ".//div[@class='c-product-tile']")
    })
    private List<WebElement> productsFromGrid;

    private Logger logger = LoggerFactory.getLogger(SubcategoryPage.class);

    public SubcategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void adds_a_product_to_shopping_bag() throws Throwable {

        quickShopButton.click();

        availableSize.click();

        addItem.click();

        quickShopCloseButton.click();

        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(
                    By.id("qsLightBox")));

        } catch (NoSuchElementException nsee) {
            logger.info("Modal element is not present, this is expected to happen");
        }

    }

    public void clicks_on_shopping_bag_link() throws Throwable {

        String subcategoryUrl = driver.getCurrentUrl();
        new WebDriverWait(driver, 10).
                until(ExpectedConditions.visibilityOf(shoppingBagLink));

        shoppingBagLink.click();

        if (subcategoryUrl.equals(driver.getCurrentUrl())) {
            shoppingBagLink.click();
        }

        if (subcategoryUrl.equals(driver.getCurrentUrl())) {
            //even though the element was clicked we did not get redirected
            //this seems to happen in PhantonJS and we need to get directly to the requested page.

            String href = shoppingBagLink.getAttribute("href");

            logger.info("click did not work, we may be using phantomjs and the link for shopping link bag does not " +
                    "work as expected for this browser, redirecting to shopping bag page {}", href);

            driver.get(href);

        }

    }

    public boolean quickShopModalIsPresent() {
        boolean isPresent;
        try {
            isPresent = quickShopModal.isDisplayed();
        } catch (NoSuchElementException nsee) {
            isPresent = false;
        }
        return isPresent;
    }

    public boolean isProductGridPresent() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(productGrid));
        return productGrid.isDisplayed();
    }

    public void hover_first_product_in_grid() {
        Actions action = new Actions(driver);
        WebElement firstProductFromGrid = productsFromGrid.get(0);
        action.moveToElement(firstProductFromGrid);
        action.perform();
    }

    private boolean isPriceAndNameValidFor(WebElement product) {
        boolean result = true;
        String productName = null;
        String productPrice = null;

        try {

            productName = product.findElement(By.className("tile__detail--name")).getText();
            productPrice = product.findElement(By.className("tile__detail--price--list")).getText();

        } catch (NoSuchElementException nsee) {
            try {

                productPrice = product.findElement(By.className("tile__detail--price--was")).getText() + " "
                        + product.findElement(By.className("tile__detail--price--sale")).getText();

            } catch (NoSuchElementException nse) {
                result = false;
            }
        }

        logger.debug("Price found for product with name {} is {}", productName, productPrice);

        if (StringUtils.isBlank(productPrice) || StringUtils.isBlank(productName)) {
            result = false;
        }

        return result;
    }

    public boolean isFirstProductNameAndPriceValid() {
        WebElement product = productsFromGrid.get(0);
        return isPriceAndNameValidFor(product);
    }

    public boolean areFirstProductColorVariationsValid() {
        WebElement firstProductFromGrid = productsFromGrid.get(0);
        return areProductColorVariationsValid(firstProductFromGrid);

    }

    private boolean areProductColorVariationsValid(WebElement firstProductFromGrid) {
        boolean result = false;
        try {
            String productCount = firstProductFromGrid.findElement(By.className("tile__detail--colors-count")).getText();
            Pattern p = Pattern.compile("available in (\\d)+ colors");
            Matcher matcher = p.matcher(productCount);
            int numberOfVariationsInText = 0;

            if (matcher.matches()) {

                String numberOfVariationsString = matcher.group(1);

                logger.debug("color is {} ", numberOfVariationsString);

                numberOfVariationsInText = Integer.parseInt(numberOfVariationsString);

                int numberOfVariationsDisplayed = Integer.parseInt(firstProductFromGrid.
                        findElement(By.className("colors__wrap")).
                        getAttribute("data-colors-count"));

                result = numberOfVariationsInText == numberOfVariationsDisplayed;

            }


        } catch (NoSuchElementException nsee) {

            logger.debug("Color count not found for product with name {}, there should not be variations",
                    firstProductFromGrid.findElement(By.className("tile__detail--name")).getText());

            result = firstProductFromGrid.findElements(By.className("colors-list__item")).isEmpty();
        }

        return result;
    }

    public boolean isProductArrayValid() {
        boolean result = true;
        for (WebElement product : productsFromGrid) {
            boolean isCorrectlyDisplayed = isPriceAndNameValidFor(product) && areProductColorVariationsValid(product);
            if (!isCorrectlyDisplayed) {
                logger.debug("The product {} contains invalid details in product grid.",
                        product.findElement(By.className("tile__detail--name")).getText());
                result = false;
                break;
            }
        }
        return result;
    }

    public void click_first_product_in_grid() {
        final WebElement firstProduct = productsFromGrid.get(0);
        final WebElement productLink = firstProduct.findElement(By.className("product-tile__link"));
        productLink.click();
    }


    public String getCategoryTitleBelowGlobalPromo() {
        return categoryPageTitle.getText();
    }

    public String getAccordianHeaderLabelText() {
        final WebElement accordionHeaderLabel = getAccordionElement(By.className("js-label"));
        return accordionHeaderLabel.getText();
    }

    public boolean isMoreIconDisplayed() {
        final WebElement accordionHeaderIcon = getAccordionElement(By.className("icon-see-more"));
        return accordionHeaderIcon.isDisplayed();
    }

    private WebElement getAccordionElement(By element) {
        final WebElement accordionWrap = new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOf(this.accordionWrap));

        final WebElement expectedElement = new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOf(accordionWrap.findElement(element)));

        return expectedElement;
    }

    public void click_expand_accordion_icon() {
        getAccordionElement(By.className("icon-see-more")).click();
    }

    public boolean isAccordionMenuVisible() {
        final WebElement accordionMenu = getAccordionElement(By.className("accordian__menu"));

        return accordionMenu.isDisplayed();
    }

    public boolean isLessIconDisplayed() {
        final WebElement accordionHeaderIcon = getAccordionElement(By.className("icon-see-less"));
        return accordionHeaderIcon.isDisplayed();
    }

    public void click_cardigans_subcategory() {
        final WebElement cardiganSubcategory = getAccordionElement(By.xpath("//a[@data-safe-name='cardigans']"));
        cardiganSubcategory.click();		
    }

    public boolean isAccordionMenuInvisible() {
        final WebElement accordionWrap = new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOf(this.accordionWrap));

        final WebElement accordionMenu = accordionWrap.findElement(By.className("accordian__menu"));

        new WebDriverWait(driver, 10).until(
                ExpectedConditions.invisibilityOfElementLocated(By.className("accordian__menu")));

        return !accordionMenu.isDisplayed();
    }

    public String getArrayLabel() {
        final WebElement arrayLabel = driver.findElement(By.id("c-product__list"));
        return arrayLabel.findElement(By.tagName("h4")).getText();
    }

    public List<String> getProductsDisplayedHrefs()
    {
        List<WebElement> products = driver.findElements(By.className("c-product-tile"));
        List<String> productsHref = new ArrayList<String>();
        for (WebElement product: products) {
            productsHref.add(product.findElement(By.tagName("a")).getAttribute("href"));
        }
        return productsHref;
    }

}
