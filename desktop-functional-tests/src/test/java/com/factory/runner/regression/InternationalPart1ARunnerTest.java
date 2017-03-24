package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/International"},
        tags = {"@International-Part1A"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-fa-international1A.json",
                "pretty",
                "html:target/cucumber/fa-international1A"
        }
)
public class InternationalPart1ARunnerTest {
}