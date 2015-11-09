package com.jcrew.runner.qa;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@SortOptions"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-SortOptions.json",
                "pretty",
                "html:target/cucumber/SortOptions"}
)

public class SortOptionsFeaturesRunnerTest {
}