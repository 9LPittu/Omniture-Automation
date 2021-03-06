package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 8/22/2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/SearchAndSale"},
        tags = {"@SaleLanding3","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-SaleLanding3.json",
                "pretty",
                "html:target/cucumber/SaleLanding3"
        }
)
public class SaleLandingPart3RunnerTest {
}
