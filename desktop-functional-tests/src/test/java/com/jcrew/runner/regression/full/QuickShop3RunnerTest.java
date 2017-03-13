package com.jcrew.runner.regression.full;

/**
 * Created by 9msyed on 11/9/2016.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/QuickShop"},
        tags = {"@Quickshop3"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-quickshop3.json",
                "pretty",
                "html:target/cucumber/quickshop3"
        }
)
public class QuickShop3RunnerTest {
}
