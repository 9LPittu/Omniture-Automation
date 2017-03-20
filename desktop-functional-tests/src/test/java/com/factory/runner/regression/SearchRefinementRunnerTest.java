package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/SearchAndSale"},
        tags = {"@SearchRefinement"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-searchRefinement.json",
                "pretty",
                "html:target/cucumber/searchRefinement"
        }
)
public class SearchRefinementRunnerTest {
}
