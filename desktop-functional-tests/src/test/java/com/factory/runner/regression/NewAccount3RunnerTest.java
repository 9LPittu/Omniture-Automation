package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/AccountFeature"},
        tags = {"@NewAccount3"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-factory-newaccountressionfeatures.json",
                "pretty",
                "html:target/cucumber/factory-newaccountregressionfeatures"
        }
)
public class NewAccount3RunnerTest {
}