package cucumber.com.jcrew.stepdefs;

import com.jcrew.cucumber.container.DomPOJO;
import com.jcrew.cucumber.view.DomView;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.GenericMethods;
import com.jcrew.helper.PropertyLoader;
import cucumber.api.java.After;
import cucumber.api.java.Before;
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
        /*JiraStepDef jiraDef = new JiraStepDef();
        jiraDef.I_m_on_the_Jira_login_page();
        jiraDef.i_login_Jcrew_Jira_application_as_and("geetha.natarajan.cft@jcrew.com", "Jcrew901722");*/
    }

    private void init() throws Throwable {
        driver = BrowserDriver.getCurrentDriver(PropertyLoader.getBrowserType());
        DomView.init();
    }

    @After("@Initialize")
    public void afterScenario() {
        BrowserDriver.close();
        DomPOJO.cleanPojo();
        List<String> errors = new ArrayList<>();
        GenericMethods.setAssertFails(errors);
    }
}