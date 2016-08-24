package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Context_Part2","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-international-context-part2-regression-features.json",
                "pretty",
                "html:target/cucumber/international-context-part2-regression-features"
        }
)

public class InternationalContextPart2RunnerTest {
}
