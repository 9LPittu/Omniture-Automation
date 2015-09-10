package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterAccountAddressBookPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaaccountaddressbookpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaaccountaddressbookpagefeatures"
        }
)

public class EmbeddedHeaderFooterAccountAddressBookPage {
}
