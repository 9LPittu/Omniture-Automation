package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/jcrew/regression/ShoppableTray",
        tags = {"@ShoppableTray"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-shoppabletrayfeatures.json",
                "pretty",
                "html:target/cucumber/shoppabletrayfeatures"}
)
public class ShoppableTrayRunnerTest {
}