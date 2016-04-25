package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.jcrew.pojo.Country;

public class SubcategoryPage {

    private final StateHolder stateHolder = StateHolder.getInstance();

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(SubcategoryPage.class);
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
    @FindBy(id = "c-product__list")
    private WebElement productList;
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
    
    @FindBy(className = "product__name")
    private WebElement productName;

    public SubcategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private WebElement getFirstProduct() {
        return getProductTileElements().get(0);
    }

    public void adds_a_product_to_shopping_bag() {

        quickShopButton.click();

        availableSize.click();

        addItem.click();

        quickShopCloseButton.click();

        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(
                    By.id("qsLightBox")));

        } catch (NoSuchElementException nsee) {
            logger.debug("Modal element is not present, this is expected to happen");
        }

    }

    public void clicks_on_shopping_bag_link() {

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

            logger.debug("click did not work, we may be using phantomjs and the link for shopping link bag does not " +
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
        Country country = (Country) stateHolder.get("context");
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        return productGrid.isDisplayed() & Util.countryContextURLCompliance(driver, country);
    }

    public void hover_first_product_in_grid() {
        Actions action = new Actions(driver);
        action.moveToElement(getFirstProduct());
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

        if (StringUtils.isBlank(productPrice) || StringUtils.isBlank(productName)) {
            result = false;
        }

        return result;
    }

    public boolean isFirstProductNameAndPriceValid() {
        return isPriceAndNameValidFor(getFirstProduct());
    }

    public boolean areFirstProductColorVariationsValid() {
        return areProductColorVariationsValid(getFirstProduct());
    }

    private boolean areProductColorVariationsValid(WebElement firstProductFromGrid) {
        boolean result = false;
        if (firstProductFromGrid.findElements(By.className("colors-list__item")).isEmpty()) {
            logger.debug("Color count not found for product with name {}, there should not be variations",
                    firstProductFromGrid.findElement(By.className("tile__detail--name")).getText());
            result = true;
        } else {
            String productCount = firstProductFromGrid.findElement(By.className("tile__detail--colors-count")).getText();
            Pattern p = Pattern.compile("available in (\\d)+ colors");
            Matcher matcher = p.matcher(productCount);
            int numberOfVariationsInText;

            if (matcher.matches()) {

                String numberOfVariationsString = matcher.group(1);

                logger.debug("color is {} ", numberOfVariationsString);

                numberOfVariationsInText = Integer.parseInt(numberOfVariationsString);

                int numberOfVariationsDisplayed = Integer.parseInt(firstProductFromGrid.
                        findElement(By.className("colors__wrap")).
                        getAttribute("data-colors-count"));

                result = numberOfVariationsInText == numberOfVariationsDisplayed;
            }
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

    public void click_first_product_in_grid() {
        Util.waitForPageFullyLoaded(driver);
        final WebElement product = getFirstProduct();
        final WebElement productLink = product.findElement(By.className("product__image--small"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(productLink));
        saveProduct(product);
        productLink.click();
        Util.waitLoadingBar(driver);
    }

    public void click_first_product_with_xpath(String finder) {
        Util.waitForPageFullyLoaded(driver);
        List<WebElement> regularPriceProducts = driver.findElements(By.xpath(finder));
        if (regularPriceProducts.size() > 0) {
            WebElement product = regularPriceProducts.get(0);
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(product.findElement(By.cssSelector(".js-product__image"))));
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(product));
            saveProduct(product);
            product.findElement(By.cssSelector(".js-product__image")).click();

        } else {
            logger.debug("No products with {} xpath; clicking first product in grid", finder);
            click_first_product_in_grid();
        }
    }

    public void click_any_product_in_grid() {
        List<WebElement> products = getProductTileElements();
        int index = Util.randomIndex(products.size());
        WebElement randomProductSelected = products.get(index);
        saveProduct(randomProductSelected);

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(randomProductSelected));
        WebElement productLink = randomProductSelected.findElement(By.className("product-tile__link"));
        productLink.click();
        Util.waitLoadingBar(driver);
    }

    private String getProductName(WebElement randomProductSelected) {
        WebElement productName = randomProductSelected.findElement(
                By.xpath(".//a[contains(@class,'product-tile__details')]/span[contains(@class,'tile__detail--name')]"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productName));
        return productName.getText().trim();
    }

    private String getProductPrice(WebElement productSelected) {
        List<WebElement> productPrices = productSelected.findElements(By.className("tile__detail--price--list"));
        String price = "";
        if (!productPrices.isEmpty()) {
            price = productPrices.get(0).getText().trim();
        }

        return price;
    }


    public String getCategoryTitleBelowGlobalPromo() {
        Util.waitWithStaleRetry(driver, categoryPageTitle);
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

        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(accordionWrap.findElement(element)));
    }

    public void click_expand_accordion_icon() {
        WebElement seeMoreIcon = getAccordionElement(By.className("icon-see-more"));
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.elementToBeClickable(seeMoreIcon));

        seeMoreIcon.click();
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

    public void click_random_filter() {
        final List<WebElement> filters = accordionWrap.findElements(By.className("accordian__menu__link"));
        final WebElement filter = filters.get(Util.randomIndex(filters.size() - 1) + 1);

        stateHolder.put("filter", filter.getText());
        filter.click();
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
        List<String> productsHref = new ArrayList<>();
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

        return productInTile.isDisplayed();
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
        WebElement priceInTile = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productGrid.findElement(By.xpath("//span[text()='" + product +
                        "' and contains(@class, 'tile__detail--name')]/../../..//img[contains(@class, 'js-product__image')]")))
        );
        return priceInTile.isDisplayed();
    }

    public boolean isImageDisplayedForProduct() {
        String product = getFirstProduct().findElement(By.className("tile__detail--name")).getText();
        WebElement priceInTile = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productGrid.findElement(By.xpath("//span[text()='" + product +
                        "' and contains(@class, 'tile__detail--name')]/../../..//img[contains(@class, 'js-product__image')]")))
        );
        return priceInTile.isDisplayed();
    }

    public String getWasPriceFor(String product) {
        WebElement wasPriceInTitle = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productGrid.findElement(By.xpath("//span[text()='" + product +
                "' and contains(@class, 'tile__detail--name')]/../div[contains(@class,'tile__detail--price--was')]"))));

        return wasPriceInTitle.getText();
    }

    public String getSalePriceFor(String product) {
        String result;
        try {
            WebElement wasPriceInTitle = productGrid.findElement(By.xpath("//span[text()='" + product +
                    "' and contains(@class, 'tile__detail--name')]/../div[contains(@class,'tile__detail--price--sale')]"));

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

    public void click_accordion_option(String option) {
        String delimiter = getDelimiter(option);
        final WebElement drawerOption = endCapNavigationSection.findElement(
                By.xpath("//h5[text() = " + delimiter + option + delimiter + "]"));
        drawerOption.click();
    }

    public boolean isDrawerClosedForOption(String menuOption) {
        String delimiter = getDelimiter(menuOption);
        By locator = By.xpath("//h5[text() = " + delimiter + menuOption + delimiter +
                "]/../ul[contains(@class, 'accordian__menu')]");
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.invisibilityOfElementLocated(locator));

        return true;
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

    public void click_on_product(String product) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        WebElement productLink = Util.createWebDriverWait(driver).
                until(ExpectedConditions.visibilityOf(productGrid.
                        findElement(By.xpath("//span[text()='" + product +
                                "' and contains(@class, 'tile__detail--name')]/.."))));

        productLink.click();
        Util.waitLoadingBar(driver);
    }

    private void saveProduct(WebElement productElement) {
        Product product = new Product();
        product.setProductName(getProductName(productElement));
        product.setPriceList(getProductPrice(productElement));

        logger.debug("Selected product is {}", product.getProductName());
        logger.debug("Selected product price is {}", product.getPriceList());

        @SuppressWarnings("unchecked")
        List<Product> productList = (List<Product>) stateHolder.get("productList");

        if (productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(product);
        stateHolder.put("productList", productList);
    }

    public void selectRandomItemAndSelectSizeColor(){

    	boolean isItemFound = false;
    	int MAX_ITEMS_TO_CHECK = 5;
    	int itemsThreshold;

    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));

    	List<WebElement> arrayPageItems = driver.findElements(By.xpath("//div[@class='c-product-tile']"));

    	//checking items are displayed on the array page
    	if(arrayPageItems.size()==0){
    		throw new WebDriverException("No items are displayed on the array page (or) element properties are changed");
    	}

    	//Setting the threshold on the items to be checked
    	if(arrayPageItems.size() > MAX_ITEMS_TO_CHECK){
    		itemsThreshold = MAX_ITEMS_TO_CHECK;
    	}
    	else{
    		itemsThreshold = arrayPageItems.size();
    	}
    	
    	String arrayPageItemName = "";
    	String arrayPageItemPrice = "";
    	for(int loopCntr=0;loopCntr<itemsThreshold;loopCntr++){

    		try{
    			arrayPageItems = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='c-product-tile']"))));

        		//Capture the item name and price on array page 		
        		List<WebElement> itemName = driver.findElements(By.xpath("//span[contains(@class,'tile__detail--name')]"));
        		WebElement currentItemOnArrayPage = itemName.get(loopCntr);
        		arrayPageItemName = currentItemOnArrayPage.getText().trim();
        		
        		List<WebElement> itemPrice = driver.findElements(By.xpath("//span[contains(@class,'tile__detail--price--list')]"));
        		arrayPageItemPrice = itemPrice.get(loopCntr).getText().trim();

        		//Click on array page item
        		currentItemOnArrayPage.click();
        		logger.debug("Array page item '{}' is clicked", arrayPageItemName);

    			//Wait till the PDP page is displayed
        		Util.waitForPageFullyLoaded(driver);        		
        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productName));
        		logger.debug("PDP page is displayed!!!");
        		
        		List<WebElement> itemColors = Util.createWebDriverWait(driver,20).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath(".//img[@class='colors-list__image']"))));

	  			//If colors are not available then navigate back to array page
	  			if(itemColors.size() == 0){
	  				navigateBackToArrayPage();
	  				continue;
	  			}
	  			else{
	  				logger.debug("Color element(s) found!!!");
	  			}

	  			//Click on each color at random and check any sizes are available
	  			String colorName = "";
	  			boolean isValidItemSizesAvailable = false;
	  			for(WebElement itemColor:itemColors){

	  				itemColor.click();

	  				//check atleast one size is available for a particular color
	  				List<WebElement> itemSizes = driver.findElements(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
	  				if(itemSizes.size() > 0){
	  					WebElement productPriceColors = driver.findElement(By.xpath("//div[@class='product__price-colors']"));
	  					colorName = productPriceColors.findElement(By.className("product__value")).getText();
	  					isValidItemSizesAvailable = true;
	  					break;
	  				}
	  			}

	  			//If there are no sizes available i.e., item is OUT OF STOCK(OOS) then navigate back to array page
	  			if(!isValidItemSizesAvailable){
	  				navigateBackToArrayPage();
	  				continue;
	  			}

	  			logger.debug("Selected item name: {}", arrayPageItemName);
	  			logger.debug("Selected item price: {}", arrayPageItemPrice);
	  			logger.debug("Selected item color: {}", colorName);

	            //Select random size from the available sizes	  			
	  			List<WebElement> itemSizes = driver.findElements(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
	  			
	  			int itemSizeIndex = Util.randomIndex(itemSizes.size());	  			
	            String sizeName = itemSizes.get(itemSizeIndex).getText();
	            itemSizes.get(itemSizeIndex).click();
	            logger.debug("Selected item size: {}", sizeName);	            

	            //Save all item related details in stateholder
	            Product product = new Product();

	            product.setProductName(arrayPageItemName);
	            product.setPriceList(arrayPageItemPrice);
	            product.setSelectedColor(colorName);
	            product.setSelectedSize(sizeName);

	            @SuppressWarnings("unchecked")
				List<Product> productList = (List<Product>) stateHolder.get("productList");

	            if (productList == null) {
	                productList = new ArrayList<>();
	            }

	            productList.add(product);
	            stateHolder.put("productList", productList);

	  			isItemFound = true;
	            break;
    		}
    		catch(Exception e){
    			try{
	    			boolean isPDP = driver.findElement(By.xpath(".//h1[@class='product__name']")).isDisplayed();
	    			if (isPDP){
	    				navigateBackToArrayPage();
	    			}
    			}
    			catch(Exception e1){
    				logger.info("PDP page is not displayed");    				
    			}
    		}
    	}

    	if(!isItemFound){
    		throw new WebDriverException("No item is found with appropriate color and size");
    	}
    }

    public void navigateBackToArrayPage(){
    	WebElement breadcrumb = Util.createWebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.className("c-header__breadcrumb"))));
		breadcrumb.findElement(By.xpath("//ul[@class='breadcrumb__list']/li[3]/a[@class='breadcrumb__link']")).click();
		Util.waitForPageFullyLoaded(driver);
		logger.debug("Navigated back to Array page");
    }
    
    public void selectRandomItemFromArrayPage(){
    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String environment = propertyReader.getProperty("environment");
    	
    	if(driver.getCurrentUrl().startsWith((environment + "/r/search/"))){
    		//for some items array page is displayed
    		click_any_product_in_grid();
    	}
    	else{
    		//for some items PDP page is displayed directly
    		saveProductDetailsFromPDPPage();
    	}
    }

    public void saveProductDetailsFromPDPPage() {

        //capture the product name
        WebElement productName = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@class='product__name']"))));
        String itemName = productName.getText();

        //Store the product details
        Product product = new Product();
        product.setProductName(itemName);

        logger.debug("Selected product is {}", product.getProductName());

        @SuppressWarnings("unchecked")
        List<Product> productList = (List<Product>) stateHolder.get("productList");

        if (productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(product);
        stateHolder.put("productList", productList);
    }

    public boolean isItemDisplayedInSearchResultsPage(String propertyName) {

        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String itemName = testDataReader.getData(System.getProperty("environment") + "." + propertyName);

        return productTileExistFor(itemName);

    }

    public boolean isPriceMatchesForSaleItem(String saleItemPropertyName, String priceType, String pricePropertyName) {

        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String itemName = testDataReader.getData(System.getProperty("environment") + "." + saleItemPropertyName);
        String expectedItemPrice = testDataReader.getData(System.getProperty("environment") + "." + pricePropertyName);

        String price;
        if (priceType.equalsIgnoreCase("was")) {
            price = getWasPriceFor(itemName).replace("was ", "");
        } else {
            price = getSalePriceFor(itemName).replace("now ", "");
        }

        return expectedItemPrice.equalsIgnoreCase(price);
    }
    
    
    public boolean isCorrectCurrencySymbolonProductGridList() {
        boolean result = true;        
        String strCurrency = (String)stateHolder.get("currency");
        
        List<WebElement> productpricess = driver.findElements(By.xpath("//span[contains(@class,'tile__detail tile__detail--price--')]"));
        	
        if(productpricess.isEmpty()) {
            logger.debug("Item Price  count not found on PDP page");
            result = true;
        } else {
        	for (WebElement price : productpricess) 
        	
        		if (!price.getText().contains(strCurrency)) {
        			result = false;
        			break;
        		}
        }
        if(result){
        	logger.info("Currency symbol is displayed correctly on all Item prices on Product grid list");
        	
        }
        else{
        	logger.debug("Currency symbol is not displayed correctly on all / any of the Item prices  on Product grid list");
        }
        return result;
    }
}