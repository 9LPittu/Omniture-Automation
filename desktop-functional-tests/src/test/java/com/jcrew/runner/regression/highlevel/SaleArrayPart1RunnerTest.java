package com.jcrew.runner.regression.highlevel;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Created by 9msyed on 8/22/2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/SearchAndSale"},
        tags = {"@SaleArray1","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-SaleArray1.json",
                "pretty",
                "html:target/cucumber/SaleArray1"
        }
)
public class SaleArrayPart1RunnerTest {
}
