package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Sale"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saleregressionfeatures.json",
                "pretty",
                "html:target/cucumber/saleregressionfeatures"
        }
)
public class SaleFeaturesRunnerTest {
}