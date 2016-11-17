package com.jcrew.steps;


import com.jcrew.page.Feature;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

public class FeatureSteps extends DriverFactory {
    Feature featurePage = new Feature(getDriver());
    
    @When("User selects a random shop this look")
    public void select_random_shop_this_look() {
    	featurePage.selectRandomShopThisLook();
    }

   
}
