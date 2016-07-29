package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Array"},
        tags = {"@Array"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-array.json",
                "pretty",
                "html:target/cucumber/array"
        }
)
public class ArrayRunnerTest {

}
