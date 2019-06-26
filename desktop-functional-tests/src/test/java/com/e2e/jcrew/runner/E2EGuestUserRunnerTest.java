package com.e2e.jcrew.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.jcrew.listners.Reporter;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/e2e/jcrew"},
        tags = {"@e2e-guestuser"},
        glue = {"com.jcrew.steps"},
        		plugin = {"com.jcrew.listners.ExtentCucumberFormatter:"}
        /*format = {
                "json:target/JC_E2E_GuestUser_Testdata_Sheet.json",
                "pretty",
                "html:target/cucumber/JC_E2E_GuestUser_Testdata_Sheet"
        }*/
)
public class E2EGuestUserRunnerTest {
		@BeforeClass
	    public static void setup() {
	        Reporter.setReportsConfig("JCFAR");
	    }

	    @AfterClass
	    public static void teardown() {
	        Reporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/properties/reports-config.xml"));
	        Reporter.setSystemInfo("user", System.getProperty("user.name"));
	    }

}