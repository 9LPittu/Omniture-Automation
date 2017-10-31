package com.factory.runner.sanity;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/"},
        tags = {"@Sanity"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-sanity.json",
                "pretty",
                "html:target/cucumber/sanity"
        }
)
public class SanityRunnerTest {
}