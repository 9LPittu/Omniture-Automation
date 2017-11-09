package com.jcrew.runner.regression.full;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Content"},
        tags = {"@JcrewContentTesting"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Content.json",
                "pretty",
                "html:target/cucumber/Content"
        }
)

public class ContentRunnerTest {
	
}








