package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/AccountFeature"},
        tags = {"@SignIn"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-signingressionfeatures.json",
                "pretty",
                "html:target/cucumber/signinregressionfeatures"
        }
)
public class SignInRunnerTest {
}
