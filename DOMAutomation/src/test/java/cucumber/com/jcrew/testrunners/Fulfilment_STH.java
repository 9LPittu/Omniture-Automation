package cucumber.com.jcrew.testrunners;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Venkat on 08-09-2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions(tags = {"@sthdom"},
        monochrome = true, plugin = {"com.cucumber.listener.ExtentCucumberFormatter:"}, features = "features", glue = {"classpath:cucumber.com.jcrew.stepdefs"})

public class Fulfilment_STH {
    @BeforeClass
    public static void setup() {
        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
        String time = sdf.format(cal.getTime());
        //DomPOJO.setReportInitTime(time);
        String path = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + "DOM_" + time;
        System.out.println(path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        extentProperties.setReportPath(path + "/report.html");
    }

    @AfterClass
    public static void teardown() {
        Reporter.loadXMLConfig(new File("src/test/resources/reports-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
    }
}