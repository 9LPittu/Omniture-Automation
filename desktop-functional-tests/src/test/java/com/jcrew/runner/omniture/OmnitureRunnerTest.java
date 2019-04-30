package com.jcrew.runner.omniture;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.jcrew.listners.Reporter;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/omniture"},
        tags = {"@s_omnitureJcrew"},
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
