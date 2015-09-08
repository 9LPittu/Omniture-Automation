package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Logo"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-logoregressionfeatures.json",
                "pretty",
                "html:target/cucumber/logoregressionfeatures"
        }
)
public class LogoFeaturesRunnerTest {
}
