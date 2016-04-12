package com.jcrew.steps;

import com.jcrew.page.SaleLandingPage;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

/**
 * Created by nadiapaolagarcia on 4/7/16.
 */
public class SaleLandingPageSteps extends DriverFactory{
    SaleLandingPage sale = new SaleLandingPage(getDriver());

    @Then("User selects random sale category")
    public void user_selects_random_sale_category() {
        sale.selectRandomSaleCategory();
    }

}
