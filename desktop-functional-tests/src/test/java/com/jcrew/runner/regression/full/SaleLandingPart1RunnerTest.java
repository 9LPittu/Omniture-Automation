package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 8/22/2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/SearchAndSale"},
        tags = {"@SaleLanding1"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-SaleLanding1.json",
                "pretty",
                "html:target/cucumber/SaleLanding1"
        }
)
public class SaleLandingPart1RunnerTest {
}
