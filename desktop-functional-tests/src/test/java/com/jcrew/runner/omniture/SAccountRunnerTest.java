package com.jcrew.runner.omniture;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/omniture"},
        tags = {"@s_account"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-SAccount.json",
                "pretty",
                "html:target/cucumber/SAccount"
        }
)
public class SAccountRunnerTest {

}
