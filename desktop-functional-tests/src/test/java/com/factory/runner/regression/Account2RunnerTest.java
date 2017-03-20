package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Account"},
        tags = {"@Account2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-factory-account2.json",
                "pretty",
                "html:target/cucumber/factory-account2"
        }
)
public class Account2RunnerTest {
}