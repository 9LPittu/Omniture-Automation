package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchPage {

    private final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final WebDriver driver;

    @FindBy(className = "header__search")
    private WebElement headerSearch;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(className = "product__grid")
    private WebElement productGrid;

    @FindBy(className = "menu__search")
    private WebElement genderSelectorElement;

    @FindBy(xpath = "//div[@data-label='Women']")
    private WebElement womenSelector;

    @FindBy(xpath = "//div[@data-group='gender']")
    private
    List<WebElement> genderSelectors;

    @FindBy(id = "c-search__results")
    private WebElement searchResult;

    @FindBy(className = "search__filter--gender")
    private WebElement genderTag;

    @FindBy(className = "search__filter--refinement")
    private WebElement searchFilterRefinementSection;

    @FindBy(className = "search__filter--actions")
    private WebElement searchFilterRefinementActions;

    @FindBy(className = "search__button--refine")
    private WebElement refineButton;

    @FindBy(className = "search__filter--sort")
    private WebElement searchFilterSortBySection;
    
    @FindBys({@FindBy(xpath="//a[contains(@class,'js-search__filter sizes-list__item') and not(contains(@class,'is-selected')) and not(contains(@class,'is-disabled'))]")})
    private List<WebElement> sizeRefinementOptions;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }


    public boolean isSearchPage() {
    	try{
	        Country country = (Country) stateHolder.get("context");
	        Util.waitWithStaleRetry(driver, headerSearch);
	        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(searchResult));
	
	        return headerSearch.isDisplayed() && searchResult.isDisplayed() && Util.countryContextURLCompliance(driver,country);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }


    public boolean areResultsDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));
        return !productGrid.findElements(By.className("c-product-tile")).isEmpty();
    }

    public List<String> areGenderSelectorsDisplayed() {
        final List<String> genderSelectorAttributes = new ArrayList<>();
        for (WebElement gender_selector : genderSelectors) {

            genderSelectorAttributes.add(gender_selector.getAttribute("data-group"));
        }
        return genderSelectorAttributes;
    }

    public void click_on_gender_selector() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(womenSelector));
        womenSelector.click();
    }

    public void click_on_gender_selector(String gender) {
        for (WebElement genderSelector : genderSelectors) {
            if (genderSelector.getText().equalsIgnoreCase(gender)) {
                genderSelector.click();
            }
        }

    }

    public boolean isRefinePage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(genderTag));
        return genderTag.isDisplayed();
    }

    public boolean isRefineButtonDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(refineButton));
        return refineButton.isDisplayed();
    }

    public void click_refine_button() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(refineButton));
        refineButton.click();
    }

    public boolean isRefinementDisplayed(String filterRefinement) {
        final WebElement filterRefinementElement = getRefinementElement(filterRefinement);
        return filterRefinementElement.isDisplayed();
    }

    private WebElement getRefinementElement(String filterRefinement) {
        WebElement element;
        try {
            element = searchFilterRefinementSection.
                    findElement(By.xpath(".//span[contains(text(), '" + filterRefinement + "') " +
                            "and @class='search__filter--label']"));
        } catch (StaleElementReferenceException sere) {
            element = searchFilterRefinementSection.
                    findElement(By.xpath(".//span[contains(text(), '" + filterRefinement + "') " +
                            "and @class='search__filter--label']"));
        }
        return element;
    }

    public boolean isSortByOptionDisplayed(String sortByOption) {
        final WebElement filterRefinementElement = searchFilterSortBySection.
                findElement(By.xpath(".//a[text()='" + sortByOption + "' and contains(@class, 'search__refinement--link')]"));
        return filterRefinementElement.isEnabled();
    }


    public boolean isSortByOptionSelected(String sortByOption) {
        sortByOption = sortByOption.toLowerCase();
        WebElement sortByOptionCheckbox = searchFilterSortBySection.findElement(
                By.xpath(".//a[ " + Util.xpathGetTextLower + "='" + sortByOption + "' " +
                        "and contains(@class, 'search__refinement--link') " +
                        "and contains(@class, 'is-selected')]"));

        return sortByOptionCheckbox.isDisplayed();
    }

    public String click_on_no_sale_price_product() {

        List<WebElement> products_displayed = productGrid.findElements(By.className("c-product-tile"));

        List<WebElement> no_sale_products = new ArrayList<>();
        for (WebElement product_displayed : products_displayed) {
            if (!product_displayed.getText().contains("now")) {
                no_sale_products.add(product_displayed);
            }
        }
        int min = 0;
        int max = no_sale_products.size() - 1;
        int range = (max - min) + 1;
        int randomNumber = (int) (Math.random() * range) + min;
        WebElement selectedNoSalePriceProduct = no_sale_products.get(randomNumber);

        WebElement selectedProductNameElement = selectedNoSalePriceProduct.findElement(By.className("tile__detail--name"));

        String productName = selectedProductNameElement.getText();
        logger.debug("sale product selected now {}", productName);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(
                selectedNoSalePriceProduct.findElement(By.className("js-product__image"))));
        selectedNoSalePriceProduct.findElement(By.className("js-product__image")).click();

        return productName;
    }


    public boolean scroll_down_the_page() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 3000);");
        return true;
    }

    public boolean scroll_up_the_page() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -500);");
        return true;
    }

    public int getProductArrayCount() {
        return productGrid.findElements(By.className("c-product-tile")).size();
    }

    public List<String> getAllProductsDisplayedFor(String productId) {
        List<WebElement> productTiles = driver.findElements(By.className("product-tile"));
        List<String> products = new ArrayList<>();
        for (WebElement productTile : productTiles) {
            String dataProductAttribute = productTile.getAttribute("data-product");
            if (dataProductAttribute.contains(productId) && dataProductAttribute.contains("color")) {
                products.add(productTile.getText());
            }
        }
        return products;
    }

    public void click_refinement(String refinement) {
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(filterRefinementElement));
        Util.scrollAndClick(driver, filterRefinementElement);

        WebElement lessIcon = filterRefinementElement.findElement(By.xpath("..//i[@class='js-icon icon-see-less']"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(lessIcon));
    }

    public boolean isOptionSelectedForRefinementWithAccordionOpen(String option, String refinement) {
        try {
            final WebElement optionElementLink = getOptionElementFromRefinement(option, refinement);
            final WebElement optionCheckbox = optionElementLink.findElement(By.xpath("preceding-sibling::input"));
            return optionCheckbox.isSelected();

        } catch (StaleElementReferenceException sere) {
            return isOptionSelectedForRefinementWithAccordionOpen(option, refinement);
        }
    }

    private WebElement getOptionElementFromRefinement(String option, String refinement) {

        final WebElement filterRefinementElement = getRefinementElement(refinement);
        final WebElement accordionMenuForRefinement =
                filterRefinementElement.findElement(By.xpath("../../div[@class='accordian__menu']"));

        return accordionMenuForRefinement.findElement(By.xpath(".//a[contains(text(), '" + option + "')]"));

    }

    private WebElement getRandomOptionFromRefinement(String refinement) {
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        final WebElement accordionMenuForRefinement =
                filterRefinementElement.findElement(By.xpath("../../div[@class='accordian__menu']"));

        List<WebElement> list = accordionMenuForRefinement.findElements(
                By.xpath("./div[@class='search__refinement--group']/a[contains(@class,'js-search__filter')]"));

        return list.get(Util.randomIndex(list.size()));
    }

    public void select_option_from_refinement(String option, String refinement) {
        WebElement optionElementLink;

        if("random".equalsIgnoreCase(option)){
            optionElementLink = getRandomOptionFromRefinement(refinement);
            stateHolder.put("randomCategory",optionElementLink.getText());

        }else {
            optionElementLink = getOptionElementFromRefinement(option, refinement);
        }
        optionElementLink.click();
        Util.waitLoadingBar(driver);
    }

    public boolean isOptionSelectedForRefinementWithAccordionClosed(String optionSelected, String refinement) {
        if("selected".equalsIgnoreCase(optionSelected)){
            optionSelected = (String) stateHolder.get("randomCategory");
        }
        optionSelected = optionSelected.toLowerCase();
        WebElement refinementElement = getRefinementElement(refinement);

        By selectedFilter = By.xpath("../span[@class='search__filter--selected']");

        WebElement selectedOption = refinementElement.findElement(selectedFilter);
        String selectedOptionText = selectedOption.getText().toLowerCase();

        return optionSelected.contains(selectedOptionText);
    }

    public void select_option_from_multiple_select_refinement(String option, String refinement) {
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        final WebElement accordionMenuForRefinement =
                filterRefinementElement.findElement(By.xpath("../../div[@class='accordian__menu']"));
        final WebElement optionElement = accordionMenuForRefinement.findElement(By.linkText(option));

        try {

            optionElement.click();

        } catch (StaleElementReferenceException sere) {
            logger.debug("Stale Element Reference Exception when trying to click " +
                    "linkText({}), retrying... ", option);
            Util.clickWithStaleRetry(optionElement);
        }

        Util.waitLoadingBar(driver);
    }

    public boolean isRefinementOpen(String refinement) {
        scroll_down_the_page();
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        final WebElement drawerIcon = filterRefinementElement.findElement(By.xpath("following-sibling::i"));
        return drawerIcon.getAttribute("class").contains("icon-see-less");
    }

    public void click_refinement_close_drawer(String refinement) {
        final WebElement filterRefinementElement = getRefinementElement(refinement);
        final WebElement drawerIcon = filterRefinementElement.findElement(
                By.xpath("following-sibling::i[contains(@class, 'icon-see-less')]"));

        try {

            drawerIcon.click();

        } catch (StaleElementReferenceException sere) {
            logger.debug("Stale Element Reference Exception when trying to click  " +
                    "following-sibling::i[contains(@class, 'icon-see-less')], retrying... ");
            Util.clickWithStaleRetry(drawerIcon);
        }
    }

    public void click_refinement_menu_done_button() {
        final WebElement doneButton = searchFilterRefinementActions.findElement(By.id("btn__search--done"));

        //required for phantomjs; selecting sort option closes refinement
        if(doneButton.isDisplayed()) {
            Util.clickWithStaleRetry(doneButton);
        }

    }

    public int getCurrentNumberOfResults() {
        String text = searchResult.findElement(By.className("search__results--count")).getText();
        text = text.replace(" result", "");
        text = text.replace("s", "");
        Integer numberOfResults = Integer.valueOf(text.replace(" results", ""));

        logger.debug("Number of results found are {}", numberOfResults);

        return numberOfResults;
    }

    public boolean isBreadcrumbDisplayedFor(String option) {
        By breadCrumbLocator = By.xpath(".//button[contains(@class, 'search__results--crumb') and contains(text(), '" + option + "')]");

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(breadCrumbLocator));

        return true;
    }
    
    @SuppressWarnings("unchecked")
	public void selectRandomOptionFromSizeRefinement(){
    	
    	if(sizeRefinementOptions.size()==0){
    		logger.error("There are no sizes to be selected");
    		throw new NoSuchElementException("There are no sizes to be selected");
    	}
    	
    	int randomNum = Util.randomIndex(sizeRefinementOptions.size());
    	WebElement sizeElement = sizeRefinementOptions.get(randomNum);
    	String selectedSizeRefinementOption = sizeElement.getAttribute("data-label").toLowerCase();
    	sizeElement.click();
    	Util.waitLoadingBar(driver);
    	
    	List<String> selectedSizeRefinementOptions = new ArrayList<String>();
    	
    	if(stateHolder.get("selectedSizeRefinementOptions")!=null){
    		selectedSizeRefinementOptions = (List<String>)stateHolder.get("selectedSizeRefinementOptions");
    		selectedSizeRefinementOptions.add(selectedSizeRefinementOption);
    		stateHolder.put("selectedSizeRefinementOptions", selectedSizeRefinementOptions);
    	}
    	else{
    		selectedSizeRefinementOptions.add(selectedSizeRefinementOption);
    		stateHolder.put("selectedSizeRefinementOptions", selectedSizeRefinementOptions);
    	}
    }
    
    public boolean isSizeRefinementValuesDisplayedCorrectly(){
    	
    	@SuppressWarnings("unchecked")
		List<String> selectedsizeRefinementOptions = (List<String>)stateHolder.get("selectedSizeRefinementOptions");
    	
    	By selectedFilter = By.xpath(".//span[contains(text(), 'Size') and @class='search__filter--label']/following::span[@class='search__filter--selected']");
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(selectedFilter));    	
        WebElement selectedOption = driver.findElement(selectedFilter);
        String actualOptionText = selectedOption.getText();
    	
        String expectedOptionText = "";
    	if(selectedsizeRefinementOptions.size() > 1){
    		expectedOptionText = selectedsizeRefinementOptions.size() + " selected";
    	}
    	else{
    		expectedOptionText = selectedsizeRefinementOptions.get(0);
    	}
    	
    	return actualOptionText.equalsIgnoreCase(expectedOptionText);
    }
    
    public boolean isSelectedOptionsDisplayedInBreadcrumb(){
    	
    	@SuppressWarnings("unchecked")
		List<String> selectedsizeRefinementOptions = (List<String>)stateHolder.get("selectedSizeRefinementOptions");
    	
    	boolean isBreadcrumbItemDisplayed = false;
    	for(String selectedSizeRefinementOption:selectedsizeRefinementOptions){
    		By breadCrumbLocator = By.xpath(".//button[contains(@class, 'search__results--crumb') and contains(text(), '" + selectedSizeRefinementOption + "')]");
    		try{
    			Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(breadCrumbLocator));
    			isBreadcrumbItemDisplayed = true;
    		}
    		catch(Exception e){
    			isBreadcrumbItemDisplayed = false;
    			break;
    		}
    	}

        return isBreadcrumbItemDisplayed;
    }
    
    public void selectRandomProduct()
    {
        selectRandomProduct(searchResult);
    }
    
    public void selectRandomProduct(WebElement productList) {
        List<WebElement> productTiles = getProductTiles(productList);
        logger.info("This array page has {} products", productTiles.size());
        
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElements(productTiles));
        WebElement random_product_tile = Util.randomIndex(productTiles);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(random_product_tile));
        WebElement random_product_name = random_product_tile.findElement(By.className("tile__detail--name"));

        logger.info("Selected product: {}", random_product_name.getText());

        WebElement random_product_image = random_product_tile.findElement(By.tagName("img"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(random_product_image));
        random_product_image.click();

        Util.waitLoadingBar(driver);
        new ProductDetailPage(driver);
    }
    
    private List<WebElement> getProductTiles(WebElement productList) {
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productList));
        return productList.findElements(By.className("c-product-tile"));
    }
}