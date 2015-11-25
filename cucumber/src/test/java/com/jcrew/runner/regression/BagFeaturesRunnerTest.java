package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Bag"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-bagregressionfeatures.json",
                "pretty",
                "html:target/cucumber/bagregressionfeatures"
        }
)
public class BagFeaturesRunnerTest {
}
