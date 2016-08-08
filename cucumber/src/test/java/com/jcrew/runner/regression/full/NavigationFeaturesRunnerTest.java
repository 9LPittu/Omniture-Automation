package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
//        tags = {"@Account,@Category,@PDP,@Bag"},
        tags = {"@AccountDev","~@wip"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-navigationregressionfeatures.json",
                "pretty",
                "html:target/cucumber/navigationregressionfeatures"
        }
)
public class NavigationFeaturesRunnerTest {
}
