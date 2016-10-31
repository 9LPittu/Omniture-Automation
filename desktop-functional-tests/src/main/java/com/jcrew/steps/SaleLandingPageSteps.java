package com.jcrew.steps;

import com.jcrew.page.SaleLanding;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;

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

}
