package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@HeaderAndFooter2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-headerregressionfeatures.json",
                "pretty",
                "html:target/cucumber/headerregressionfeatures"
        }
)
public class HeaderAndFooterFeaturesRunnerTest {
}
