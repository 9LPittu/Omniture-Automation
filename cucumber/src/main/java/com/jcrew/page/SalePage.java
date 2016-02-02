package com.jcrew.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

/**
 * Created by 9hvenaga on 11/24/2015.
 */
public class SalePage {


    private final Logger logger = LoggerFactory.getLogger(SalePage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final PropertyReader reader = PropertyReader.getPropertyReader();

    private final WebDriver driver;

    @FindBys({@FindBy(className = "menu__link--has-href")})
    private List<WebElement> saleCategoryLinks;

    @FindBy(className="header__sale")
    private WebElement saleHeader;

    @FindBy(className="js-search__button--refine")
    private WebElement refineButton;

    @FindBy(className="search__filter--gender")
    private WebElement genderInFilterName;

    @FindBy(className="search__filter--actions")
    private WebElement refinementSection;

    @FindBys({@FindBy(className="search__filter--label")})
    private List<WebElement> filterLabel;

    @FindBy(className="search__filter--sort")
    private WebElement sortBySection;

    @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[1]/a[1]")
    private WebElement sortSectionFirstOption;

    @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[1]/a[text()='New in Sale']")
    private WebElement newInSaleText;

    @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[2]/a[text()='Price: Low to High']")
    private WebElement priceLowToHighText;

    @FindBy(xpath=".//section[contains(@class,'search__filter--sort')]/descendant::div[3]/a[text()='Price: High to Low']")
    private WebElement priceHighToLowText;

    @FindBy(xpath=".//a[@data-label='New in Sale']")
    private WebElement newInSaleCheckBox;

    @FindBy(xpath=".//a[@data-label='Low-High']")
    private WebElement priceLowToHighCheckBox;

    @FindBy(xpath=".//a[@data-label='High-Low']")
    private WebElement priceHighToLowCheckBox;

    @FindBy(id="btn__search--done")
    private WebElement refinePageDoneButton;

    @FindBys({@FindBy(className="tile__detail--price--sale")})
    private List<WebElement> salePrice;

    @FindBy(className="pagination__item--page-select")
    private WebElement pagination;

    @FindBy(xpath="//li[contains(@class,'pagination__item--previous')]/descendant::span[1]")
    private WebElement leftPagination;

    @FindBy(xpath="//li[contains(@class,'pagination__item--previous')]/descendant::span[contains(@class,'is-disabled')]")
    private WebElement leftPaginationDisabled;

    @FindBy(xpath="//li[contains(@class,'pagination__item--next')]/descendant::a[1]")
    private WebElement rightPagination;

    @FindBy(className="js-product__quantity")
    private WebElement paginationDropdown;

    @FindBy(className="c-sale__promo-alert")
    private WebElement secondPromo;

    @FindBy(xpath = "//span[@class='c-label__details' and text()='DETAILS']")
    private WebElement saleDetailsLink;

    @FindBy(id = "c-sale-details")
    private WebElement saleDetails;

    @FindBy(xpath = "//section[@class='search__filter--sort search__section']")
    private WebElement sortSection;

    @FindBy(className = "c-search__filter--refinement")
    private WebElement refineModal;


    public SalePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public boolean isSalePageDisplayed(){
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(saleHeader));
        return saleHeader.isDisplayed();
    }

    public boolean isSaleLandingPage() {
        String saleLandingUrl =reader.getProperty("environment")+"/r/sale";
        return driver.getCurrentUrl().equals(saleLandingUrl);
    }

    public boolean isSaleTitleDisplayed() {
        WebElement saleTitle = driver.findElement(By.xpath("//div[@class='c-sale__title' and text() = 'SALE']"));
        return saleTitle.isDisplayed();
    }
    public boolean  isFirstPromoDisplayed() {
        WebElement firstPromo = driver.findElement(By.className("c-sale__promo-frame"));
        logger.debug("promo message and code :" + firstPromo.getText());
        return firstPromo.isDisplayed()&&firstPromo.getText()!=null;
    }

    public boolean isRefineButtonDisplayed(){
        return refineButton.isDisplayed();
    }

    public boolean isSelectedCategoryDisplayed(String category){
        return genderInFilterName.getText().trim().equalsIgnoreCase(category);
    }

    public void clickRefineButton(){
        //wait for the load bar to disappear
        Util.waitLoadingBar(driver);

        refineButton.click();
    }

    public boolean isRefinementPageDisplayed(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(refineModal));
        return refinementSection.isDisplayed();
    }

    public boolean isNewInSaleCheckboxSelectedByDefault(){
        String cssClass = newInSaleCheckBox.getAttribute("class");
        logger.debug("class: {}", cssClass);

        return cssClass.contains("is-selected");
    }

    public boolean isNewInSaleSortOptionSelectedByDefault() {
        return newInSaleText.getAttribute("class").contains("is-selected");
    }


    public boolean isSortSectionFirstOptionDisplayed(String firstSortOption){
       return sortSectionFirstOption.getText().trim().toLowerCase().equals(firstSortOption.toLowerCase());
    }

    public boolean isSortOptionsDisplayed(String sortByFilterOption){

        boolean blnFlag = false;

        List<WebElement> sortOptions = sortBySection.findElements(By.className("js-search__sort"));
        for(int i=0;i<sortOptions.size();i++){
            String sortOptionName = sortOptions.get(i).getText().trim();
            if(sortOptionName.equalsIgnoreCase(sortByFilterOption)){
                blnFlag = true;
                break;
            }
        }

        return blnFlag;
    }

    public boolean isSortOptionSelected(WebElement option) {
        return option.getAttribute("class").contains("is-selected");
    }

    public boolean isOtherSortOptionsUnchecked(String sortByOption1, String sortByOption2){

        boolean blnFlag = false;

        sortByOption1 = sortByOption1.toLowerCase();
        sortByOption2 = sortByOption2.toLowerCase();

        switch (sortByOption1){
            case "new in sale":
                blnFlag = blnFlag || isSortOptionSelected(newInSaleText);
                break;
            case "price: high to low":
                blnFlag = blnFlag || isSortOptionSelected(priceHighToLowText);
                break;
            case "price: low to high":
                blnFlag = blnFlag || isSortOptionSelected(priceLowToHighText);
                break;
            default:

        }

        switch (sortByOption2){
            case "new in sale":
                blnFlag = blnFlag || isSortOptionSelected(newInSaleText);
                break;
            case "price: high to low":
                blnFlag = blnFlag || isSortOptionSelected(priceHighToLowText);
                break;
            case "price: low to high":
                blnFlag = blnFlag || isSortOptionSelected(priceLowToHighText);
                break;
            default:

        }

        return !blnFlag;
    }

    public void selectSortOptionCheckbox(String sortOption){
        String refineModalClass = refineModal.getAttribute("class");
        if(refineModalClass.contains("hidden")){
            clickRefineButton();
        }
        final WebElement sortOptionElement = sortSection.findElement(By.xpath(".//a[contains(@class," +
                 "'js-search__sort search__refinement--link') and " +
                Util.xpathGetTextLower + " = '"
                + sortOption.toLowerCase() + "']"));

        sortOptionElement.click();

        //wait for the load bar to disappear
        Util.waitLoadingBar(driver);

    }

    public void clickDoneButton() throws InterruptedException{

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(refinePageDoneButton));
        refinePageDoneButton.click();
    }

    public boolean isSalePricesAreSorted(String sortOrder){
        boolean blnFlag = false;

        //return false if objects are not found
        if(salePrice.size() > 0){
            List<Double> lstSalePrices = new ArrayList<Double>();

            //Capture all the prices into double list
            for(int i = 0;i < salePrice.size();i++){
                String salePriceVal = salePrice.get(i).getText().toLowerCase();
                salePriceVal = salePriceVal.replaceAll("[^0-9\\.]", "");
                lstSalePrices.add(Double.parseDouble(salePriceVal.trim()));
            }

            List<Double> ascending = new ArrayList<Double>(lstSalePrices);
            Collections.sort(ascending);

            List<Double> descending = new ArrayList<Double>(ascending);
            Collections.reverse(descending);

            if(sortOrder.toLowerCase().contains("low to high")){
                blnFlag = lstSalePrices.equals(ascending);
            }
            else if(sortOrder.toLowerCase().contains("high to low")){
                blnFlag = lstSalePrices.equals(descending);
            }
        }

        return blnFlag;
   }

    public boolean isLast4WeeksDisplayed() {

        return driver.findElement(By.xpath("//*[@id='c-search__results']/div/div[2]/button")).isDisplayed();
    }

    public boolean isPaginationDisplayed(){

        boolean blnFlag;

        WebElement searchResultsCount = driver.findElement(By.className("search__results--count"));
        String resultsCount = searchResultsCount.getText().toLowerCase().replace("results", "").trim();
        Integer resultsNumber = Integer.parseInt(resultsCount);

        //If the search results is > 60
        if(resultsNumber > 60 && pagination.isDisplayed()){
            blnFlag = true;
        }
        else{
            blnFlag = false;
        }

        return blnFlag;
    }

    public boolean isPageUrlContains(String url){
        return driver.getCurrentUrl().toLowerCase().contains(url.toLowerCase());
    }

    public boolean isLeftNavigationTextDisplayedAsPrev(String leftPaginationText){
        return leftPagination.getText().trim().equalsIgnoreCase(leftPaginationText);
    }

    public boolean isLeftNavigationTextPrevIsDisabled(){
        return leftPaginationDisabled.isDisplayed();
    }

    public boolean isRightNavigationTextDisplayedAsNext(String rightPaginationText){
        return rightPagination.getText().trim().equalsIgnoreCase(rightPaginationText);
    }

    public boolean isRightNavigationTextNextIsActive(){
        return rightPagination.isEnabled();
    }

    public boolean isPaginationDropdownDisplaysCurrentPageNumber(){
        String pageString = driver.findElement(By.xpath("//span[contains(text(),'Page')]")).getText().toUpperCase();
        Select list = new Select(paginationDropdown);
        String currentPageNumber = list.getFirstSelectedOption().getText();
        String totalPages = driver.findElement(By.xpath("//select[contains(@class,'js-product__quantity')]/" +
                "following-sibling::span[1]")).getText().toUpperCase();

        String paginationText = pageString + currentPageNumber + totalPages;

        return paginationText.matches("^PAGE \\d+ OF \\d+$");
    }

    public void selectRandomPageNumberFromPaginationDropdown(){

        List<WebElement> arrayPageItemNames = driver.findElements(By.className("tile__detail--name"));
        stateHolder.put("firstPageItemName",arrayPageItemNames.get(0).getText());

        Select list = new Select(paginationDropdown);
        int randomNumber = Util.randomIndex(list.getOptions().size());
        list.selectByIndex(randomNumber);

        //wait till page is changed
        Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("Npge=" + String.valueOf(randomNumber+1)));
    }

    public boolean isCorrectPageDisplayedWhenPageNumberChanged(){
        List<WebElement> arrayPageItemNames = driver.findElements(By.className("tile__detail--name"));

        String currentPageFirstItemName = arrayPageItemNames.get(0).getText();
        String firstPageFirstItemName = (String) stateHolder.get("firstPageItemName");

        System.out.println("Current Page Item:" + currentPageFirstItemName);
        System.out.println("First Page Item:" + firstPageFirstItemName);

        return !currentPageFirstItemName.equalsIgnoreCase(firstPageFirstItemName);
    }

    public void clickSaleLinkFromTopNav() {
        driver.findElement(By.xpath("//span[contains(@class, 'department-nav__text') and text() = 'sale']")).click();
    }

    public void clickOnSaleDept(String dept) {
        WebElement saleDepElement;
        if("random".equalsIgnoreCase(dept)){
            saleDepElement = getRandomSaleDept();
        }else{
            saleDepElement = driver.findElement(By.xpath("//a[@class='js-sale__link' and @data-label='"+dept+"']"));
        }
        saleDepElement.click();
    }

    public WebElement getRandomSaleDept(){
        List<WebElement> saleDepts = driver.findElements(By.className("js-sale__link"));
        //exclude NEW IN SALE
        int random = Util.randomIndex(saleDepts.size()-1);
        return saleDepts.get(random + 1);
    }

    public boolean isSaleCategoryLinkDisplayed(String category) {
        return driver.findElement(By.xpath("//a[@class='js-sale__link' and @data-label='"+category+"']")).isDisplayed();
    }

    public boolean isCaratSignDisplayed(String category)  {
        WebElement saleCategorylink = driver.findElement(By.xpath("//a[@class='js-sale__link' and @data-label='"+category+"']"));
        return saleCategorylink.findElement(By.className("c-category__arrow")).isDisplayed();
    }

    public boolean isSecondPromoDisplayed() {
        return secondPromo.isDisplayed();
    }

    public boolean isSecondPromoSaleCategoryLinkDisplayed(String link) {
        String saleCategory = link.trim().toLowerCase();
        WebElement secondPromoLink = secondPromo.findElement(
                By.xpath("./a[translate(text(), 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz') = '" +
                        saleCategory + "']"));
        return secondPromoLink.isDisplayed();
    }

    public void clickOnSecondPromoSaleCategoryLink(String link)  {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(secondPromo));
        secondPromo.findElement(By.linkText(link)).click();
    }

    public boolean  isDetailsLinkDisplayed() {
        return saleDetailsLink.isDisplayed();

    }

    public void clickOnDetailsLink(){
        saleDetailsLink.click();
    }

    public boolean isDisclaimerTextDisplayed() {
        WebElement saleDetailsText= saleDetails.findElement(By.className("c-sale__c-details"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(saleDetailsText));
        return saleDetailsText.getAttribute("class").contains("is-open");
    }

    public void clickDetailsCloseIcon() {
        saleDetails.findElement(By.className("icon-close")).click();
    }

    public boolean isDetailsSectionClosed() {
        WebElement saleDetailsText= saleDetails.findElement(By.className("c-sale__c-details"));
        return !(saleDetailsText.getAttribute("class").contains("is-open"));
    }
}