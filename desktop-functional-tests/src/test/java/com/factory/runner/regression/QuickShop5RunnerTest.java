package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/QuickShop"},
        tags = {"@Quickshop5"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-quickshop5.json",
                "pretty",
                "html:target/cucumber/quickshop5"
        }
)
public class QuickShop5RunnerTest {
}