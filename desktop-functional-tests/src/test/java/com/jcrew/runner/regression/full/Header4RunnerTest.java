package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 12/14/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/HeaderAndFooter"},
        tags = {"@Header4"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Header4.json",
                "pretty",
                "html:target/cucumber/Header4"
        }
)
public class Header4RunnerTest {
}
