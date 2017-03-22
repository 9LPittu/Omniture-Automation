package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/QuickShop"},
        tags = {"@Quickshop"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-factory-quickshop.json",
                "pretty",
                "html:target/cucumber/factory-quickshop"
        }
)
public class QuickShopRunnerTest {
}