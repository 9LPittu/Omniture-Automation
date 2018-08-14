package com.factory.runner.omniture;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
//import org.junit.runner.RunWith;

import com.jcrew.listners.Reporter;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/omniture"},
        tags = {"@s_omnitureArray"},
        glue = {"com.jcrew.steps"},
        plugin = {"com.jcrew.listners.ExtentCucumberFormatter:"},
        format = {
                "json:target/cucumber-SAccount.json",
                "pretty",
                "html:target/cucumber/SAccount"}
)

public class OmnitureRunnerTest {
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
