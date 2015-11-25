package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Footer"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-footerregressionfeatures.json",
                "pretty",
                "html:target/cucumber/footerregressionfeatures"
        }
)
public class FooterFeaturesRunnerTest {
}
