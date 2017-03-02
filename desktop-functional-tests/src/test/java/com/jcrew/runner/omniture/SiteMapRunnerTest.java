package com.jcrew.runner.omniture;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/omniture"},
        tags = {"@Sitemap"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-sitemap.json",
                "pretty",
                "html:target/cucumber/Sitemap"
        }
)
public class SiteMapRunnerTest {

}
