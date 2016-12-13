package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by msayed3 on 11/27/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/HeaderAndFooter"},
        tags = {"@Footer","@Header","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-HeaderFooter.json",
                "pretty",
                "html:target/cucumber/HeaderFooter"
        }
)
public class HeaderFooterRunnerTest {
}
