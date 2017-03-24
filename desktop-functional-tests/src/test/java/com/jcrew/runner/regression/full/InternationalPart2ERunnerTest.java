package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/International"},
        tags = {"@International-Part2E"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-international2E.json",
                "pretty",
                "html:target/cucumber/international2E"
        }
)
public class InternationalPart2ERunnerTest {
}