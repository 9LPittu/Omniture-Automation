package com.jcrew.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features",
        glue = {"com.jcrew.steps"},
        format = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"}
)
public class CucumberRunnerTest {
}
