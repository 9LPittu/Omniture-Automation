package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/QuickShop"},
        tags = {"@Quickshop7"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-quickshop7.json",
                "pretty",
                "html:target/cucumber/quickshop7"
        }
)
public class QuickShop7RunnerTest {
}