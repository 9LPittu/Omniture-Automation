package com.jcrew.runner.regression.full;

/**
 * Created by 9msyed on 11/9/2016.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/QuickShop"},
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
