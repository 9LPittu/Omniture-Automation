package com.jcrew.runner.regression.highlevel;

/**
 * Created by 9msyed on 11/9/2016.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/QuickShop"},
        tags = {"@Quickshop2","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-quickshop2.json",
                "pretty",
                "html:target/cucumber/quickshop2"
        }
)
public class QuickShop2RunnerTest {
}
