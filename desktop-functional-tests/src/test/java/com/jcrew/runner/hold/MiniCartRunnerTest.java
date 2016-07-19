package com.jcrew.runner.hold;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@MiniCart"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-minicartfeatures.json",
                "pretty",
                "html:target/cucumber/minicartfeatures"
        }
)
public class MiniCartRunnerTest {

}
