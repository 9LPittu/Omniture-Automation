package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/regression",
        tags = {"@PDP"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdpregressionfeatures.json",
                "pretty",
                "html:target/cucumber/pdpregressionfeatures"
        }
)

public class PDPFeaturesRunnerTest
{
}
