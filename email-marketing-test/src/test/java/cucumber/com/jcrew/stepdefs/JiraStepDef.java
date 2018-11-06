package cucumber.com.jcrew.stepdefs;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.cucumber.container.JiraContainer;
import com.jcrew.cucumber.view.JiraView;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.GenericMethods;
import com.jcrew.helper.PropertyLoader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Venkat on 02-05-2017.
 */
public class JiraStepDef {

    @Given("^I am on the JIRA application login page$")
    public void I_m_on_the_Jira_login_page() throws Throwable {
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String url = PropertyLoader.getServerURL();
        BrowserDriver.loadPage(url);
    }

    @Given("^I login Jcrew Jira application as \"(.*?)\" and \"(.*?)\"$")
    public void i_login_Jcrew_Jira_application_as_and(String username, String password) throws Throwable {
        JiraView.loginUser(username, password);
        JiraStepDef jiraDef = new JiraStepDef();
        jiraDef.I_should_see_the_jira_dashboard();
    }

    @Then("^I should see Jira dashboard$")
    public void I_should_see_the_jira_dashboard() throws Throwable {
        JiraView.waitForDashboard();
    }

    @When("^I open \"([^\"]*)\"$")
    public void I_open_jira_ticket(String ticketNumber) throws Throwable {
        JiraView.openTicket(ticketNumber);
        EmailMarketingPOJO pojo = new EmailMarketingPOJO();
        pojo.setJiraTicket(ticketNumber);
        Reporter.addStepLog("Opened Jira ticket number \"" + ticketNumber + "\"");
    }

    @When("^I open \"([^\"]*)\" Dashboard$")
    public void I_Open(String dash) throws Throwable {
        JiraView.openJiraDashboard(dash);
    }

    @When("^I extract all tickets which are assigned to QA for entire month in \"([^\"]*)\" Dashboard$")
    public void I_test_all_tickets_in_monthly_calender(String dash) throws Throwable {
        Map<String, Set<String>> tckets = JiraView.getBusinessEmailTicketsFromDashboards(dash);
        Set<String> alltckets = new HashSet<>();
        Set<String> qaTckets = new HashSet<>();
        for (String tckt : tckets.keySet()) {
            alltckets.addAll(tckets.get(tckt));
        }

        EmailMarketingPOJO.setDashboradTickets(alltckets);
        for (String tkt : alltckets) {
            if (JiraView.isTicketAssignedToQa(tkt)) {
                String[] tckt = tkt.split("/");
                qaTckets.add(tckt[tckt.length - 1]);
                break;
            }
        }
        EmailMarketingPOJO.setDashboradQATickets(qaTckets);
    }

    @Then("^I download all attachments$")
    public void I_dowload_attachments() throws Throwable {
        JiraView.downloadEachAttachment();
    }

    @Then("^I should be able to extract all fields from jira ticket$")
    public void I_get_fields() throws Throwable {
        JiraView.getJiraData();
    }
}