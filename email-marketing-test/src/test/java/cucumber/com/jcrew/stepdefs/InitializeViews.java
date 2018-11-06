package cucumber.com.jcrew.stepdefs;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.cucumber.view.EmailView;
import com.jcrew.cucumber.view.JiraView;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.GenericMethods;
import com.jcrew.helper.PropertyLoader;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Venkat on 8/9/17.
 */
public class InitializeViews {

    protected WebDriver driver = null;
    private static boolean dunit = false;

    @Before("@Initialize")
    public void beforeScenario() throws Throwable {
        init();
        JiraStepDef jiraDef = new JiraStepDef();
        jiraDef.I_m_on_the_Jira_login_page();
         jiraDef.i_login_Jcrew_Jira_application_as_and("9lperuma", "Jira@123"); 
    } 

    private void init() throws Throwable {
        driver = BrowserDriver.getCurrentDriver(PropertyLoader.getBrowserType());
        JiraView.init();
        EmailView.init();
    }

    @After("@Initialize")
    public void afterScenario() {
        BrowserDriver.close();
        EmailMarketingPOJO.cleanPojo();
        List<String> errors = new ArrayList<>();
        GenericMethods.setAssertFails(errors);
    }
}