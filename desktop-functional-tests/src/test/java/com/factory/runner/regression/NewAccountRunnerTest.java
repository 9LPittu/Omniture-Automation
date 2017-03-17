package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Account"},
        tags = {"@NewAccount"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-factory-newaccounterrorregressionfeatures.json",
                "pretty",
                "html:target/cucumber/factory-newaccounterrorregressionfeatures"
        }
)
public class NewAccountRunnerTest {
}