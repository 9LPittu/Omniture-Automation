package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/SearchAndSale"},
        tags = {"@Search2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-search2.json",
                "pretty",
                "html:target/cucumber/search2"
        }
)
public class Search2RunnerTest {
}
