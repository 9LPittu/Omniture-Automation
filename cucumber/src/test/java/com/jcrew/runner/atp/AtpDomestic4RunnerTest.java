package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPDomestic4"},
        glue = {"com.jcrew.steps"},
        format = {
        		"json:target/cucumber-atp4.json",
                "pretty",
                "html:target/cucumber/atp4"
        }
)
public class AtpDomestic4RunnerTest {
}
