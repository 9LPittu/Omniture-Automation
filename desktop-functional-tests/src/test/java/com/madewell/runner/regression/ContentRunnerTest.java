package com.madewell.runner.regression;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/madewell/regression/Content"},
        tags = {"@MadewellContentTesting"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Content.json",
                "pretty",
                "html:target/cucumber/Content"
        }
)

public class ContentRunnerTest {
	
}








