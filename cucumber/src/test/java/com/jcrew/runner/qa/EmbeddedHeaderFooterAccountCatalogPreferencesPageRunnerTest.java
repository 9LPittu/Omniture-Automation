package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterAccountCatalogPreferencesPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaaccountcatalogpreferencespagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaaccountcatalogpreferencespagefeatures"
        }
)

public class EmbeddedHeaderFooterAccountCatalogPreferencesPageRunnerTest {
}
