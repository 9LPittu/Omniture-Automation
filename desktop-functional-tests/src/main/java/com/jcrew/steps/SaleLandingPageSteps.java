package com.jcrew.steps;

import com.jcrew.page.SaleLanding;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Collections;

/**
 * Created by nadiapaolagarcia on 4/7/16.
 */
public class SaleLandingPageSteps extends DriverFactory{
    SaleLanding sale = new SaleLanding(getDriver());

    @Then("User selects random sale category")
    public void user_selects_random_sale_category() {
        sale.selectRandomSaleCategory();
    }

    @When("User selects ([^\"]*) dept from sales$")
    public void user_selects_dept_from_sale(String saleDept) {
        sale.click_on_sale_subcategory(saleDept);
    }
    
    @Then("Verify sale landing page is displayed$")
    public void verify_sale_landing_page() {
    	assertTrue("Sale landing pagec should be displayed ", sale.isSalelanding());
    	
    }
    
    @When("Verify following links are displayed on sale landing page$")
    public void verify_sale_landing_links(List<String> saleCategories) {
    	List<String> categoryNames = sale.getSaleCategory();   		
    	
    	for (String category:saleCategories) 
    	assertTrue("Sale Category " + category + " is available in sale landing page ",categoryNames.contains(category.toLowerCase()));
    	
    }
    
    @Then("Verify Sale title is displayed on sale landing page$") 
    public void verify_sale_title(){
    	assertTrue("Sale title should be displayed on sale landing page",sale.isSaleTitle());
    }
    
    @Then("Verify first promo is displayed$")
    public void verify_first_promo() {
    	assertTrue("First promo should be displayed on sale landing page",sale.isFirstPromo());
    }
    
    @Then("User clicks on details link on first promo$") 
    public void click_details_link() {
    	sale.clickDetailsLink();
    }
    
    @Then("Verify promo details pop up is ([^\"]*)$")
    public void verify_promo_details_popup(String expectedState) {
    	expectedState = expectedState.toLowerCase().trim();
    	String actualState = sale.getPromoPopUpState();

    	assertEquals("Promo details pop up should be ",expectedState,actualState);
    }
    
    @Then("User clicks on close icon on promo detail pop up$")
    public void close_promo_detail() {
    	sale.closePromoDetails();
    }
    
    @Then("Verify Second promo is displayed on sale landing page$")
    public void verify_second_promo() {
    	assertTrue("Second promo should be displayed on sale landing page",sale.isSecondPromo());
    }
    
    @When("User clicks on ([^\"]*) link from second promo$")
    public void click_second_promo_link(String gender) {
    	sale.clickSecondPromoLink(gender);
    }

    @When("User Selects random sale category from the list$")
    public void select_random_sale_category_from_list(List<String> saleCategories) {
    	int index = Util.randomIndex(saleCategories.size());
		String randomCategory = saleCategories.get(index).toLowerCase().trim();
		
		sale.click_on_sale_subcategory(randomCategory);
    	
    }

}
