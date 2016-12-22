package com.jcrew.steps;

import com.jcrew.page.GenderLanding;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import org.junit.Assert;

/**
 * Created by 9msyed on 12/12/2016.
 */
public class GenderLandingSteps extends DriverFactory{
    GenderLanding genderLanding = new GenderLanding(getDriver());

    @Then("Verify gender landing page is displayed")
    public void verify_gender_landing_page_displayed(){
       Assert.assertTrue("Verify Gender landing page displayed",genderLanding.isLandingPageDisplayed());
    }
}
