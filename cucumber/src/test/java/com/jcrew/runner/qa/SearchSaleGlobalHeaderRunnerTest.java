package com.jcrew.runner.qa;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@SearchSaleGlobalHeader"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-searchsaleglobalheaderfeatures.json",
                "pretty",
                "html:target/cucumber/searchsaleglobalheaderfeatures"}
)

public class SearchSaleGlobalHeaderRunnerTest {

}
