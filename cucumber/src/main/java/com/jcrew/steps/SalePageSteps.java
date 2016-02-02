package com.jcrew.steps;

import com.jcrew.page.SalePage;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.*;

import static org.junit.Assert.*;

public class SalePageSteps extends DriverFactory {

    private final SalePage salePage = new SalePage(getDriver());

    @Then("^User is in Sale results page$")
    public void verify_sale_page_displayed(){
        assertTrue("Sale page should be displayed",salePage.isSalePageDisplayed());
            }

    @And("^User is in sale landing page$")
    public void verify_sale_landing_page(){
        assertTrue("User should be in sale landing page",salePage.isSaleLandingPage());
    }

    @And("^Sale title is displayed$")
    public void verify_sale_title_is_displayed(){
        assertTrue("Sale title should be displayed",salePage.isSaleTitleDisplayed());
    }

    @And("^First promo is displayed with promo message and promo code")
    public void verify_first_promo_is_displayed() {
        assertTrue("First promo should be displayed",salePage.isFirstPromoDisplayed());
    }

    @And("^([^\"]*) Category link and carat sign is displayed")
    public void verify_category_link_and_carat_sign_is_displayed(String category) {
        assertTrue("sale category link should be displayed",salePage.isSaleCategoryLinkDisplayed(category));
        assertTrue("carat sign on the sale category box should be displayed",salePage.isCaratSignDisplayed(category));
    }

    @And("^verify REFINE button is displayed$")
    public void verify_refine_button_displayed(){
        assertTrue("Refine button should be displayed",salePage.isRefineButtonDisplayed());
    }

    @And("^default filter name displayed is ([^\"]*)$")
    public void verify_default_filter_displayed(String category){
        assertTrue("Selected category name should be displayed",salePage.isSelectedCategoryDisplayed(category));
    }

    @Then("^click on REFINE button$")
    public void click_refine_button(){
        salePage.clickRefineButton();
    }

    @And("^User is in refine page$")
    public void verify_refinement_page_displayed(){
        assertTrue("Refinement Page should be displayed", salePage.isRefinementPageDisplayed());
    }

    @Then("^NEW IN SALE checkbox is selected by default$")
    public void verify_new_in_sale_checkbox_selected_default(){
        salePage.isNewInSaleCheckboxSelectedByDefault();
    }

    @Then("^NEW IN SALE sort option is selected by default$")
    public void verify_new_in_sale_sort_option_is_selected_by_default(){
        assertTrue("New in sale sort option should be selected by default", salePage.isNewInSaleSortOptionSelectedByDefault());
    }

    @Then("^first sort option is ([^\"]*)$")
    public void verify_first_sort_option(String firstSortOption){
        assertTrue("First option in sort section is "+firstSortOption,salePage.isSortSectionFirstOptionDisplayed(firstSortOption));
    }

    @Then("^first option NEW IN SALE is selected by default$")
    public void verify_first_sort_option_selected_by_default(){
        assertTrue("Sort section first option NEW IN SALE is selected by default", salePage.isNewInSaleSortOptionSelectedByDefault());
    }

    @Then("sort by options ([^\"]*) and ([^\"]*) are unchecked$")
    public void verify_other_sort_options_unchecked(String sortByOption1, String sortByOption2){
       assertTrue("Sort options " + sortByOption1 + " and " + sortByOption2 +" are unchecked",salePage.isOtherSortOptionsUnchecked(sortByOption1,sortByOption2));
    }    

    @Then("^([^\"]*) is displayed as sort option$")
    public void verifySortOptionsDisplayed(String sortOption){
        assertTrue("Sort option " + sortOption + " is displayed",salePage.isSortOptionsDisplayed(sortOption));
    }

    @Then("^select ([^\"]*) checkbox$")
    public void select_sort_checkbox(String sortOption){
       salePage.selectSortOptionCheckbox(sortOption);
    }

    @Then("^click on DONE button on Refine page$")
    public void click_DONE_Button() throws InterruptedException{
        salePage.clickDoneButton();
    }

    @Then("^sale prices are sorted correctly when ([^\"]*) is selected$")
    public void verify_Sale_Prices_Sorting(String sortOption){
       assertTrue("Sale prices should be sorted correctly when " + sortOption + " is selected", salePage.isSalePricesAreSorted(sortOption));
    }

    @And("^The products are sorted by New in sale$")
    public void verify_display_last_four_weeks() {
        assertTrue("Products should be sorted by new in sale", salePage.isLast4WeeksDisplayed());
    }
    
    @And("^pagination is displayed on the page$")
    public void pagination_displayed_on_page(){  	
    	assertTrue("Pagination should be displayed when items on the page are more than 60/Pagination should not be displayed when items on the page are less than 60", salePage.isPaginationDisplayed());    	
    }
    
    @And("^the page url should contain \"([^\"]*)\"")
    public void page_url_should_contain(String url){
    	assertTrue("Page URL should contain " + url, salePage.isPageUrlContains(url));
    }
    
    @And("^left pagination text should be ([^\"]*)$")
    public void left_pagination_text_is_prev(String lextPaginationText){
    	assertTrue("Left pagination text should be " + lextPaginationText,salePage.isLeftNavigationTextDisplayedAsPrev(lextPaginationText));
    }
    
    @And("^left pagination text PREV should be in disabled state by default$")
    public void left_pagination_text_prev_is_disabled(){
    	assertTrue("Left pagination text PREV should be in disabled state by default",salePage.isLeftNavigationTextPrevIsDisabled());
    }
    
    @And("^right pagination text should be ([^\"]*)$")
    public void right_pagination_text_is_next(String rightPaginationText){
    	assertTrue("Right pagination text should be " + rightPaginationText,salePage.isRightNavigationTextDisplayedAsNext(rightPaginationText));
    }
    
    @And("^right pagination text NEXT should be in active state$")
    public void right_pagination_text_next_is_enabled(){
    	assertTrue("Right pagination text NEXT should be in active state",salePage.isRightNavigationTextNextIsActive());
    }
    
    @And("^pagination dropdown should display current page number$")
    public void pagination_dropdown_displays_current_page_number(){
    	assertTrue("Pagination dropdown should display current page number",salePage.isPaginationDropdownDisplaysCurrentPageNumber());
    }
    
    @And("^select random page number from pagination dropdown$")
    public void select_page_number_from_pagaination_dropdown(){
    	salePage.selectRandomPageNumberFromPaginationDropdown();
    }
    
    @And("^user should be displayed with correct page when page number is changed$")
    public void validate_correct_page_displayed_when_page_number_changed(){
    	assertTrue("user should be displayed with correct page when page number is changed", salePage.isCorrectPageDisplayedWhenPageNumberChanged());
    }

    @And("^User clicks on sale link from top nav$")
    public void click_on_sale_link_from_top_nav() {
        salePage.clickSaleLinkFromTopNav();
    }

    @And("^User clicks on sale department ([^\"]*)$")
    public void click_on_sale_dept(String dept) {
        salePage.clickOnSaleDept(dept);
    }

    @And("^Second promo is displayed$")
    public void verify_second_promo_is_displayed() {
        assertTrue("second promo on the sale landing page should be displayed", salePage.isSecondPromoDisplayed());
    }

    @And("^([^\"]*)sale category link is displayed in the second promo$")

    public void verify_second_promo_sale_category_link_is_displayed(String link) {
        assertTrue("sale link should be displayed in th esecond promo",salePage.isSecondPromoSaleCategoryLinkDisplayed(link));
    }

    @When("^([^\"]*) in sale page is clicked$")
    public void click_on_the_second_promo_link(String link) {
        salePage.clickOnSecondPromoSaleCategoryLink(link);
    }

    @And("^Details link is displayed$")
    public void verify_details_link_is_displayed() {
        assertTrue("details link on sale page should be displayed", salePage.isDetailsLinkDisplayed());
    }

    @And("^Click on details link")
    public void click_on_the_details_link() {
        salePage.clickOnDetailsLink();
    }

    @And("^Promo legal text is displayed$")
    public void verify_legal_disclaimer_is_displayed() {
        assertTrue("Legal disclaimer text should be displayed", salePage.isDisclaimerTextDisplayed());
    }

    @And("^Click on details section close icon$")
    public void click_on_details_close_icon() {
        salePage.clickDetailsCloseIcon();
    }

    @And("^Details section is closed$")
    public void verify_details_section_is_closed() {
       assertTrue("Details section should be closed",salePage.isDetailsSectionClosed());
    }

}