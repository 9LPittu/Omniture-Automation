package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 3/30/16.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/AccountFeature"},
        tags = {"@NewAccount","@Account"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-newaccountressionfeatures.json",
                "pretty",
                "html:target/cucumber/newaccountregressionfeatures"
        }
)
public class NewAccountRunnerTest {
}
