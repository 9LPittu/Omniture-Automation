package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/International"},
        tags = {"@International-Part2B"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-fa-international2B.json",
                "pretty",
                "html:target/cucumber/fa-international2B"
        }
)
public class InternationalPart2BRunnerTest {
}