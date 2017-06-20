package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Account"},
        tags = {"@NewAccount3"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-factory-newaccountregressionfeatures.json",
                "pretty",
                "html:target/cucumber/factory-newaccountregressionfeatures"
        }
)
public class NewAccount3RunnerTest {
}