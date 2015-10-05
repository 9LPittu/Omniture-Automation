package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@Demo"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/demo.json","html:target/demo"}
)
public class DemoFeaturesRunnerTest {
}
