package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@1account,@2home,@3category,@4pdp"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-accountregressionfeatures.json",
                "pretty",
                "html:target/cucumber/accountregressionfeatures"
        }
)
public class AccountFeaturesRunnerTest {
}
