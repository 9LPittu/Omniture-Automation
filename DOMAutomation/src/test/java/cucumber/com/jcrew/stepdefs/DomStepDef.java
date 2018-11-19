package cucumber.com.jcrew.stepdefs;

import com.jcrew.cucumber.view.DomView;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.ExcelUtils;
import com.jcrew.helper.PropertyLoader;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.util.concurrent.TimeUnit;

public class DomStepDef {
    @Given("^I am on the DOM application login page$")
    public void I_m_on_the_Jira_login_page() throws Throwable {
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        BrowserDriver.loadPage("http://bwi-esdom-q11.jcrew.com:30000/");
    }

    @Given("^I login DOM application as \"(.*?)\" and \"(.*?)\"$")
    public void i_login_Jcrew_Jira_application_as_and(String username, String password) throws Throwable {
        DomView.loginUser(username, password);
    }

    @When("^I select \"(.*?)\" from menu$")
    public void i_select_menu(String username) throws Throwable {
    	DomView.selectMenu();
    }

    @When("^I click on apply button$")
    public void clickOnApply() throws Throwable {
        DomView.clickOnApply();
    }

    /*@When("^I enter order number \"(.*?)\"$")*/
    @When("^I enter order number$")
    public void enterOrderNum(/*String orederNum*/) throws Throwable {
    	DomView.enterOrderNum(/*orederNum*/);
    }

    @When("^I should see the requested order in search results page$")
    public void serachResultsVerify() throws Throwable {
        DomView.verifyOrderInSearchResults();
    }

    @When("^the order should be in \"(.*?)\" status in search results page$")
    public void verifyOrderStatusInSearchResulst(String status) throws Throwable {
        DomView.verifyOrderStatus(status);
    }

    @When("^I double click on order$")
    public void doubleckOnOrder() throws Throwable {
        DomView.doubleClickOnOrder();
    }

    @When("^I select \"(.*?)\" option$")
    public void selectOption(String option) throws Throwable {
        DomView.selectOption(option);
    }

    @When("^Order details page should display$")
    public void oderDetailsPageShouldDisplay() throws Throwable {
        DomView.verifyOrderDetailsPageShouldDisplay();
    }

    @When("^order status should change to \"(.*?)\"$")
    public void doStatusInAddpage(String doStatus) throws Throwable {
        DomView.verifyDoStatusFromAdditionDetailsPage(doStatus);
    }

    @When("^order status should change to \"(.*?)\" in Distribution orders page$")
    public void doStatusInDopage(String doStatusInDoPage) throws Throwable {
        DomView.verifyDoStatusFromDoPage(doStatusInDoPage);
    }

    @When("^I click on Line additional details$")
    public void i_click_on_Line_additional_details() {
        DomView.clickOnLineAddDetails();
    }

    @When("^I select all line ids$")
    public void i_select_all_line_ids() {
        DomView.checkOnLines();
    }

    @When("^I click on create DO$")
    public void i_click_on_create_DO() {
        DomView.clickOnCreateDo();
    }

    @When("^I copy DO numbers$")
    public void copyDo() {
        //BrowserDriver.getCurrentDriver().get("http://bwi-esdom-q12.jcrew.com:30000/ucl/auth/jsp/home.jsp");
        DomView.copyDoNum();
    }

    @When("^I select distribution orders from menu$")
    public void selectDcOrdersFromMenu() {
        DomView.clickOnDisOrdersMenu();
    }

    @When("^I enter copied distribution id$")
    public void enterDcOrderId() {
        DomView.enterDcOrderId();
    }

    @When("^I click on apply button in distribution orders page$")
    public void clickOnApplyInDcOrders() {
        DomView.clickOnApplyInDisOrders();
    }

    @When("^I double click DO in distribution orders page$")
    public void doubbleClickOnDoOrder() {
        DomView.doubleClickOnDoOrder();
    }

    @When("^I click on release DO button$")
    public void clickOnRelaseBtn() {
        DomView.clickOnReleaseBtn();
    }

    @And("^I release DOs from either DC or store based on fulfillment facility$")
    public void iReleaseDOsFromEitherDCOrStoreBasedOnFulfillmentFacility() throws Throwable {
        DomView.releaseOrders();
    }
}
