package com.jcrew.runner.qa;

/**
 * Created by 9hvenaga on 9/3/2015.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterBillingPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqabillingpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqabillingpagefeatures"
        }
)
public class EmbeddedHeaderFooterBillingPageRunnerTest {
}
