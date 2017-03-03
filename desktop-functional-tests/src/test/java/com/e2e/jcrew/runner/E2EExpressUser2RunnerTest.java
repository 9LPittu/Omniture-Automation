package com.e2e.jcrew.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/e2e/jcrew"},
        tags = {"@e2e-expressuser-2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/JC_E2E_ExpressUser_Testdata_Sheet_2.json",
                "pretty",
                "html:target/cucumber/JC_E2E_ExpressUser_Testdata_Sheet_2"
        }
)
public class E2EExpressUser2RunnerTest {
}