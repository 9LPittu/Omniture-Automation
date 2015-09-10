package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterAccountOrderDetailPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaaccountorderdetailpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaaccountorderdetailpagefeatures"
        }
)

public class EmbeddedHeaderFooterAccountOrderDetailPage {
}
