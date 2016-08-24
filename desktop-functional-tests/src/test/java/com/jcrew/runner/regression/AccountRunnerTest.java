package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 8/22/2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/AccountFeature"},
        tags = {"@Account"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-newaccountressionfeatures.json",
                "pretty",
                "html:target/cucumber/newaccountregressionfeatures"
        }
)
public class AccountRunnerTest {
}
