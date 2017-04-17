package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/ShoppingBag"},
        tags = {"@shoppingbag6"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-shopping-bag-promos-part6.json",
                "pretty",
                "html:target/cucumber/cucumber-shopping-bag-promos-part6"
        })

public class ShoppingBag6RunnerTest {
}