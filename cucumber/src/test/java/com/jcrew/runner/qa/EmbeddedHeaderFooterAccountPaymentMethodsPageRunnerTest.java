package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterAccountPaymentMethodsPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaaccountpaymentmethodspagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaaccountpaymentmethodspagefeatures"
        }
)

public class EmbeddedHeaderFooterAccountPaymentMethodsPageRunnerTest {
}
