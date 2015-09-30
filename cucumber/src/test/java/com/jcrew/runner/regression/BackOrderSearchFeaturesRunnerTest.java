package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/smoke"},
        tags = {"@BackOrderSearch"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-searchregressionfeatures.json",
                "pretty",
                "html:target/cucumber/searchregressionfeatures"
        }
)
public class BackOrderSearchFeaturesRunnerTest {
}
