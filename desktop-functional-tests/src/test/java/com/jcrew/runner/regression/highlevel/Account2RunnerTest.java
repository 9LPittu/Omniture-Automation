package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 8/22/2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/AccountFeature"},
        tags = {"@Account2","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Account2.json",
                "pretty",
                "html:target/cucumber/Account2"
        }
)
public class Account2RunnerTest {
}
