package cucumber.com.jcrew.testrunners;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.helper.GenericMethods;
import cucumber.api.CucumberOptions;
import cucumber.api.java.Before;
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
@CucumberOptions(tags = {"@emailmarketing"},
        monochrome = true, plugin = {"com.cucumber.listener.ExtentCucumberFormatter:"}, features = "features/emailmarketing.feature", glue = {"classpath:cucumber.com.jcrew.stepdefs"})

public class EmailMarketingTest {
    @BeforeClass
    public static void setup() {
        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
        String time = sdf.format(cal.getTime());
        EmailMarketingPOJO.setReportInitTime(time);
        String path = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + "JEMA_" + time;
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