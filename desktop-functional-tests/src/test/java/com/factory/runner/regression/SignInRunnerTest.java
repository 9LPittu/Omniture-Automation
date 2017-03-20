package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Account"},
        tags = {"@SignIn"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-factory-signingressionfeatures.json",
                "pretty",
                "html:target/cucumber/factory-signinregressionfeatures"
        }
)
public class SignInRunnerTest {
}