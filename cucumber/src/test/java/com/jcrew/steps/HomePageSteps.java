package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;

public class HomePageSteps extends DriverFactory {

    @When("^Selects a category$")
    public void selects_a_category() {
        new HomePage(driver).selects_a_category();
    }
}
