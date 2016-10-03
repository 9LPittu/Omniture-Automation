package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@AccountDropdown2","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-account-dropdown.json",
                "pretty",
                "html:target/cucumber/regression-account-dropdown"
        }
)
public class AccountDropdownRunnerTest2 {
}