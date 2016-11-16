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
        tags = {"@SaleArray1"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-SaleArray1.json",
                "pretty",
                "html:target/cucumber/SaleArray1"
        }
)
public class SaleArrayPart1RunnerTest {
}
