package com.jcrew.runner.regression.highlevel;

/**
 * Created by 9hvenaga on 4/21/2016.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Context_Part3","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-international-context-part3-regression-features.json",
                "pretty",
                "html:target/cucumber/international-context-part3-regression-features"
        }
)

public class InternationalContextPart3RunnerTest {
}
