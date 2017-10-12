package com.jcrew.runner.sanity;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression"},
        tags = {"@Sanity"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Account2.json",
                "pretty",
                "html:target/cucumber/Account2"
        }
)

public class SanityRunnerTest {

}
