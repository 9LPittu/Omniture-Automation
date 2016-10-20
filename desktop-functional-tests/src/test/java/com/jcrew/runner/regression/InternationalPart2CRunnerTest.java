package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/International"},
        tags = {"@International-Part2C"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-international2C.json",
                "pretty",
                "html:target/cucumber/international2C"
        }
)
public class InternationalPart2CRunnerTest {

}
