package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/ShoppingBag"},
        tags = {"@shoppingbag5", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-shopping-bag-promos-part5.json",
                "pretty",
                "html:target/cucumber/cucumber-shopping-bag-promos-part5"
        })

public class ShoppingBag5RunnerTest {
}