package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/QuickShop"},
        tags = {"@Quickshop6"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-quickshop6.json",
                "pretty",
                "html:target/cucumber/quickshop6"
        }
)
public class QuickShop6RunnerTest {
}