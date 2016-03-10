package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/regression",
        tags = {"@ShoppableTray"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-shoppabletrayfeatures.json",
                "pretty",
                "html:target/cucumber/shoppabletrayfeatures"}
)
public class ShoppableTrayRunnerTest {


}
