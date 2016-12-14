package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 12/14/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/HeaderAndFooter"},
        tags = {"@Header"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-HeaderFooter.json",
                "pretty",
                "html:target/cucumber/HeaderFooter"
        }
)
public class HeaderRunnerTest {
}