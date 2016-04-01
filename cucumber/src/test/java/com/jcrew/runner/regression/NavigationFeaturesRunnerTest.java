package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        //tags = {"@Account,@Category,@PDP,@Bag"},
        		tags = {"@Bag1"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-navigationregressionfeatures.json",
                "pretty",
                "html:target/cucumber/navigationregressionfeatures"
        }
)
public class NavigationFeaturesRunnerTest {
}
