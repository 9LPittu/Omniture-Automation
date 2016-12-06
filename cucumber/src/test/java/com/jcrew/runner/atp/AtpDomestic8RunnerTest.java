package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPDomestic8"},
        glue = {"com.jcrew.steps"},
        format = {
        		"json:target/cucumber-atp8.json",
                "pretty",
                "html:target/cucumber/atp8"
        }
)
public class AtpDomestic8RunnerTest {
}
