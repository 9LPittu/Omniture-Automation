package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@PDPSizeAndFit"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdp-size-and-fit.json",
                "pretty",
                "html:target/cucumber/pdp-size-and-fit"
        }
)
public class PDPSizeAndFitRunnerTest {

}
