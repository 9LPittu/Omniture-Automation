package com.jcrew.runner.qa;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@SearchSale"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-searchsalepagefeatures.json",
                "pretty",
                "html:target/cucumber/searchsalepagefeatures"}
)


public class SearchSalePageFeaturesRunnerTest {

}
