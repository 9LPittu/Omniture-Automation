package com.jcrew.runner.regression.full;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.jcrew.listners.Reporter;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Content"},
        tags = {"@JcrewContentTesting"},
        glue = {"com.jcrew.steps"},
        		plugin = {"com.jcrew.listners.ExtentCucumberFormatter:"},
        format = {
                /*"json:target/cucumber-Content_Jcrew.json",
                "pretty",
                "html:target/cucumber/Content_Jcrew"*/
        }
)

public class ContentRunnerTest {
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








