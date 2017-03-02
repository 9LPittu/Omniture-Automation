package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/SearchAndSale"},
        tags = {"@Search","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-search.json",
                "pretty",
                "html:target/cucumber/search"
        }
)
public class SearchRunnerTest {
}
