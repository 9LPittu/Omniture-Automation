package com.factory.runner.regression;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Content"},
        tags = {"@FactoryContentTesting"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Content.json",
                "pretty",
                "html:target/cucumber/Content"
        }
)

public class ContentRunnerTest {
	
}








