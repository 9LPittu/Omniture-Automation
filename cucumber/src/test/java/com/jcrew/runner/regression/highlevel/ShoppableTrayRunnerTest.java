package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/regression",
        tags = {"@ShoppableTray", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-shoppabletrayfeatures.json",
                "pretty",
                "html:target/cucumber/shoppabletrayfeatures"}
)
public class ShoppableTrayRunnerTest {


}
