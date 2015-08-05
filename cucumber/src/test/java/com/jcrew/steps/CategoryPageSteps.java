package com.jcrew.steps;

import com.jcrew.page.CategoryPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;

public class CategoryPageSteps extends DriverFactory {

    @When("^Selects a subcategory$")
    public void selects_a_subcategory() throws Throwable {

        new CategoryPage(getDriver()).selects_a_subcategory();

    }
}
