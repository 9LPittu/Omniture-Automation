package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/AccountFeature"},
        tags = {"@SignIn","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-signingressionfeatures.json",
                "pretty",
                "html:target/cucumber/signinregressionfeatures"
        }
)
public class SignInRunnerTest {
}
