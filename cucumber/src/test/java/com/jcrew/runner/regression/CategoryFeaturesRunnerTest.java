package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Category"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-categoryregressionfeatures.json",
                "pretty",
                "html:target/cucumber/categoryregressionfeatures"
        }
)
public class CategoryFeaturesRunnerTest {
}
