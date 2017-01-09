package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 12/14/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/HeaderAndFooter"},
        tags = {"@Header3","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Header3.json",
                "pretty",
                "html:target/cucumber/Header3"
        }
)
public class Header3RunnerTest {
}
