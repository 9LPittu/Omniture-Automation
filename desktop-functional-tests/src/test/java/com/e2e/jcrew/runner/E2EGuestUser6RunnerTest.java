package com.e2e.jcrew.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/e2e/jcrew"},
        tags = {"@e2e-guestuser-6"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/JC_E2E_GuestUser_Testdata_Sheet_6.json",
                "pretty",
                "html:target/cucumber/JC_E2E_GuestUser_Testdata_Sheet_6"
        }
)
public class E2EGuestUser6RunnerTest {
}