package com.jcrew.page;

import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubcategoryPage {

    private StateHolder stateHolder = StateHolder.getInstance();

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

    @FindBy(className = "header__image")
    private WebElement headerImage;

    @FindBy(className = "c-header__main")
    private WebElement mainMenu;

    @FindBy(className = "header__promo__wrap--desktop")
    private WebElement headerDesktopPromo;

    @FindBy(className = "header__promo__wrap--mobile")
    private WebElement headerMobilePromo;

    @FindBy(className = "c-category__filters")
    private WebElement refinement;

    @FindBy(css = "header h4")
    private List<WebElement> postSignElements;

    @FindBy(css = "#c-category__filters .accordian__menu .accordian__menu__item .accordian__menu__link")
    private List<WebElement> accordionElements;

    @FindBy(id = "c-category__navigation")
    private WebElement endCapNavigationSection;

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
            Util.createWebDriverWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(
                    By.id("qsLightBox")));

        } catch (NoSuchElementException nsee) {
            logger.info("Modal element is not present, this is expected to happen");
        }

    }

    public void clicks_on_shopping_bag_link() throws Throwable {

        String subcategoryUrl = driver.getCurrentUrl();
        Util.createWebDriverWait(driver).
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
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        return productGrid.isDisplayed();
    }

    public void hover_first_product_in_grid() {
        Actions action = new Actions(driver);
        List<WebElement> productsFromGrid = getProductTileElements();
        WebElement firstProductFromGrid = productsFromGrid.get(0);
        action.moveToElement(firstProductFromGrid);
        action.perform();
    }

    private List<WebElement> getProductTileElements() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        return Util.createWebDriverWait(driver).
                until(ExpectedConditions.visibilityOfAllElements(productGrid.findElements(By.className("c-product-tile"))));
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
        WebElement product = getProductTileElements().get(0);
        return isPriceAndNameValidFor(product);
    }

    public boolean areFirstProductColorVariationsValid() {
        WebElement firstProductFromGrid = getProductTileElements().get(0);
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
        for (WebElement product : getProductTileElements()) {
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

    public void click_first_product_in_grid() throws InterruptedException {
        final WebElement firstProduct = getProductTileElements().get(0);
        final WebElement productLink = firstProduct.findElement(By.className("js-product__image"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(productLink));
        productLink.click();
    }

    public void click_any_product_in_grid() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        int index = Util.randomIndex(getProductTileElements().size());
        WebElement randomProductSelected = getProductTileElements().get(index);
        Product product = new Product();
        product.setProductName(getProductName(randomProductSelected));
        product.setPriceList(getPriceList(randomProductSelected));
        product.setVariations(getVariations(randomProductSelected));
        product.setColorsCount(getColorsCount(randomProductSelected));
        product.setPriceWas(getPriceWas(randomProductSelected));
        product.setPriceSale(getPriceSale(randomProductSelected));

        List<Product> productList = (List<Product>) stateHolder.get("productList");

        if (productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(product);

        stateHolder.put("productList", productList);

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(randomProductSelected));

        WebElement productLink = randomProductSelected.findElement(By.className("product-tile__link"));

        productLink.click();

    }

    private String getPriceSale(WebElement randomProductSelected) {
        List<WebElement> priceWasElements = randomProductSelected.findElements(By.className("tile__detail--price--sale"));
        String priceWas = null;
        if (!priceWasElements.isEmpty()) {
            priceWas = priceWasElements.get(0).getAttribute("innerHTML");
        }
        return priceWas;

    }

    private String getPriceWas(WebElement randomProductSelected) {
        List<WebElement> priceWasElements = randomProductSelected.findElements(By.className("tile__detail--price--was"));
        String priceWas = null;
        if (!priceWasElements.isEmpty()) {
            priceWas = priceWasElements.get(0).getAttribute("innerHTML");
        }
        return priceWas;
    }

    private String getPriceList(WebElement randomProductSelected) {
        try {
            List<WebElement> priceListElements = randomProductSelected.findElements(By.className("tile__detail--price--list"));
            String priceList = null;
            if (!priceListElements.isEmpty()) {
                priceList = priceListElements.get(0).getAttribute("innerHTML");
            }
            return priceList;
        } catch (StaleElementReferenceException sere) {
            return getPriceList(randomProductSelected);
        }
    }

    private String getProductName(WebElement randomProductSelected) {
        List<WebElement> productNameElements = randomProductSelected.findElements(By.className("tile__detail--name"));
        String productName = null;
        if (!productNameElements.isEmpty()) {
            productName = productNameElements.get(0).getAttribute("innerHTML");
        }
        return productName;
    }

    private String getColorsCount(WebElement randomProductSelected) {
        List<WebElement> colorCountElements = randomProductSelected.findElements(By.className("tile__detail--colors-count"));
        String colorCount = null;
        if (!colorCountElements.isEmpty()) {
            colorCount = colorCountElements.get(0).getAttribute("innerHTML");
            colorCount = colorCount.replace("available in ", "");
            colorCount = colorCount.replace(" colors", "");
        }
        return colorCount;
    }

    private String getVariations(WebElement randomProductSelected) {

        List<WebElement> variationsElement = randomProductSelected.findElements(By.className("tile__detail--alsoin"));
        String variations = null;
        if (!variationsElement.isEmpty()) {
            variations = variationsElement.get(0).getAttribute("innerHTML");
            variations = variations.replace("also in: ", "");
        }
        return variations;

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
        final WebElement accordionWrap = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(this.accordionWrap));

        final WebElement expectedElement = Util.createWebDriverWait(driver).until(
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

    public void click_subcategory(String subcategory) {
        final WebElement subcategoryElement = getAccordionElement(By.xpath("//a[@data-safe-name='" + subcategory + "']"));
        subcategoryElement.click();
    }

    public boolean isAccordionMenuInvisible() {
        final WebElement accordionWrap = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(this.accordionWrap));

        final WebElement accordionMenu = accordionWrap.findElement(By.className("accordian__menu"));

        Util.createWebDriverWait(driver).until(
                ExpectedConditions.invisibilityOfElementLocated(By.className("accordian__menu")));

        return !accordionMenu.isDisplayed();
    }

    public String getArrayLabel() {
        final WebElement arrayLabel = driver.findElement(By.id("c-product__list"));
        return arrayLabel.findElement(By.tagName("h4")).getText();
    }

    public List<String> getProductsDisplayedHrefs() {
        List<WebElement> products = driver.findElements(By.className("c-product-tile"));
        List<String> productsHref = new ArrayList<String>();
        for (WebElement product : products) {
            productsHref.add(product.findElement(By.tagName("a")).getAttribute("href"));
        }
        return productsHref;
    }

    public boolean isCategoryHeaderPresent() {
        boolean result;
        try {
            result = categoryPageTitle.isDisplayed();
        } catch (NoSuchElementException nsee) {
            result = false;
        }
        return result;
    }

    public String getCategoryImageHeaderAlt() {
        return headerImage.getAttribute("alt");
    }

    public boolean productTileExistFor(String product) {
        WebElement productInTile = productGrid.findElement(By.xpath("//span[text()='" + product +
                "' and contains(@class, 'tile__detail--name')]"));
        logger.info(productInTile.getText());

        return productInTile.isDisplayed();
    }

    public String yellowProductTileExist() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement yellow_product = productGrid.findElement(By.xpath("//*[@id='c-search__results']/div/div[3]/div[1]/div/div[2]/a/span[1]"));
        logger.info(yellow_product.getText());
        return yellow_product.getText();
    }

    public String getPriceFor(String product) {
        WebElement priceInTile = productGrid.findElement(By.xpath("//span[text()='" + product +
                "' and contains(@class, 'tile__detail--name')]/../span[contains(@class,'tile__detail--price--list')]"));
        return priceInTile.getText();
    }

    public String getAvailableColorsMessageFor(String product) {
        WebElement priceInTile = productGrid.findElement(By.xpath("//span[text()='" + product +
                "' and contains(@class, 'tile__detail--name')]/../span[contains(@class,'tile__detail--colors-count')]"));
        return priceInTile.getText();
    }

    public boolean isImageDisplayedFor(String product) {
        WebElement priceInTile = productGrid.findElement(By.xpath("//span[text()='" + product +
                "' and contains(@class, 'tile__detail--name')]//img[contains(@class, 'js-product__image')"));
        return priceInTile.isDisplayed();
    }

    public String getWasPriceFor(String product) {
        WebElement wasPriceInTitle = productGrid.findElement(By.xpath("//span[text()='" + product +
                "' and contains(@class, 'tile__detail--name')]/../span[contains(@class,'tile__detail--price--was')]"));

        return wasPriceInTitle.getText();
    }

    public String getSalePriceFor(String product) {
        String result;
        try {
            WebElement wasPriceInTitle = productGrid.findElement(By.xpath("//span[text()='" + product +
                    "' and contains(@class, 'tile__detail--name')]/../span[contains(@class,'tile__detail--price--sale')]"));

            result = wasPriceInTitle.getText();
        } catch (StaleElementReferenceException sere) {
            result = getSalePriceFor(product);
        }
        return result;
    }

    public Point getMenuPosition() {
        return mainMenu.getLocation();
    }

    public Point getDesktopPromoPosition() {
        return headerDesktopPromo.getLocation();
    }

    public Point getMobilePromoPosition() {
        return headerMobilePromo.getLocation();
    }

    public Point getRefinementPosition() {
        return refinement.getLocation();
    }

    public Point getCategoryImagePosition() {
        return headerImage.getLocation();
    }

    public boolean isDesktopPromoDisplayed() {
        return headerDesktopPromo.isDisplayed();
    }

    public List<String> getPostSignItems() {
        List<String> postSignList = new ArrayList<>();
        for (WebElement postSignElement : postSignElements) {
            postSignList.add(postSignElement.getText());
        }
        return postSignList;
    }

    public List<String> getAccordionItems() {
        List<String> accordionItemList = new ArrayList<>();
        for (WebElement postSignElement : accordionElements) {
            accordionItemList.add(postSignElement.getText());
        }
        return accordionItemList;
    }

    public String getEndCapNavigationMenuHeader() {
        return endCapNavigationSection.findElement(By.tagName("h4")).getText();
    }

    public List<String> getEndCapNavigationMenuOptions() {
        List<String> menuOptions = new ArrayList<>();
        for (WebElement menuOptionElement : endCapNavigationSection.findElements(By.tagName("h5"))) {
            menuOptions.add(menuOptionElement.getAttribute("textContent"));
        }
        return menuOptions;
    }

    public boolean isEndCapMoreIconDisplayed() {
        return endCapNavigationSection.findElement(By.className("icon-see-more")).isDisplayed();
    }

    public void click_expand_accordion_icon_for_drawer_option(String option) {
        click_icon_type(option, "icon-see-more");
    }

    private void click_icon_type(String option, String iconType) {
        String delimiter = getDelimiter(option);
        final WebElement drawerOption = endCapNavigationSection.findElement(
                By.xpath("//h5[text() = " + delimiter + option + delimiter + "]/i[contains(@class, '" +
                        iconType + "')]"));
        drawerOption.click();
    }

    public boolean isDrawerClosedForOption(String menuOption) {
        String delimiter = getDelimiter(menuOption);
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//h5[text() = " + delimiter + menuOption + delimiter +
                                "]/../ul[contains(@class, 'accordian__menu')]")
                )
        );
        WebElement drawerOption = endCapNavigationSection.findElement(
                By.xpath("//h5[text() = " + delimiter + menuOption + delimiter +
                        "]/../ul[contains(@class, 'accordian__menu')]"));

        return !drawerOption.isDisplayed();
    }

    private String getDelimiter(String menuOption) {
        return menuOption.contains("'") ? "\"" : "'";
    }

    public boolean isDrawerOpenForOption(String menuOption) {
        String delimiter = getDelimiter(menuOption);
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(
                        endCapNavigationSection.findElement(
                                By.xpath("//h5[text() = " + delimiter + menuOption + delimiter +
                                        "]/../ul[contains(@class, 'accordian__menu')]")
                        )
                )
        );

        WebElement drawerOption = endCapNavigationSection.findElement(
                By.xpath("//h5[text() = " + delimiter + menuOption + delimiter +
                        "]/../ul[contains(@class, 'accordian__menu')]"));

        return drawerOption.isDisplayed();
    }

    public void click_collapse_accordion_icon_for_drawer_option(String option) {
        click_icon_type(option, "icon-see-less");
    }

    public void click_on_product(String product) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        WebElement productLink = Util.createWebDriverWait(driver).
                until(ExpectedConditions.visibilityOf(productGrid.
                        findElement(By.xpath("//span[text()='" + product +
                                "' and contains(@class, 'tile__detail--name')]/.."))));

        productLink.click();
    }
}
