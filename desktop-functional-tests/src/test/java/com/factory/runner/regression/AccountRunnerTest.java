package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Account"},
        tags = {"@Account"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/factory-account.json",
                "pretty",
                "html:target/cucumber/factory-account"
        }
)
public class AccountRunnerTest {
}