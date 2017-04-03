package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/ShoppingBag"},
        tags = {"@shoppingbag1", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-shopping-bag-promos-part1.json",
                "pretty",
                "html:target/cucumber/cucumber-shopping-bag-promos-part1"
        })

public class ShoppingBag1RunnerTest {
}