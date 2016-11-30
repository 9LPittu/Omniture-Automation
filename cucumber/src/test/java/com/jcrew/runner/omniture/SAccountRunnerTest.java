package com.jcrew.runner.omniture;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/omniture"},
        tags = {"@s_account"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-s_account.json",
                "pretty",
                "html:target/cucumber/s_accountfeatures"
        }
)

public class SAccountRunnerTest {
}
