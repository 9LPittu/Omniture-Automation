package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@AccountDropdown1","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-account-dropdown1.json",
                "pretty",
                "html:target/cucumber/regression-account-dropdown1"
        }
)
public class AccountDropdownRunnerTest1 {
}