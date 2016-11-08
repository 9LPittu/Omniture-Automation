package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 8/22/2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/SearchAndSale"},
        tags = {"@SaleArray"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-SaleArray.json",
                "pretty",
                "html:target/cucumber/SaleArray"
        }
)
public class SaleArrayRunnerTest {
}
