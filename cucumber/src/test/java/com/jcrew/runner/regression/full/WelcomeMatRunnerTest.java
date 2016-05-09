package com.jcrew.runner.regression.full;

/**
 * Created by 9hvenaga on 4/25/2016.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@WelcomeMat"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-welcomematregressionfeatures.json",
                "pretty",
                "html:target/cucumber/welcomematregressionfeatures"
        }
)

public class WelcomeMatRunnerTest {
}
