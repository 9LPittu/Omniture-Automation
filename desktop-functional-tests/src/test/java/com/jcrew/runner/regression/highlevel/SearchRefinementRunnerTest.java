package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/SearchAndSale"},
        tags = {"@SearchRefinement","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-searchRefinement.json",
                "pretty",
                "html:target/cucumber/searchRefinement"
        }
)
public class SearchRefinementRunnerTest {
}
