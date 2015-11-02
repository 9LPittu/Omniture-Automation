package com.jcrew.steps;


import com.jcrew.page.CreateAccountPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

public class CreateAccountPageSteps extends DriverFactory {

    private final CreateAccountPage createAccountPage = new CreateAccountPage(getDriver());

    @And("^Fills required account data in create account page$")
    public void fills_required_account_data_in_create_account_page() throws Throwable {
        createAccountPage.fill_account_data();
    }

    @And("^Clicks on create new account in create account page$")
    public void clicks_on_create_new_account_in_create_account_page() throws Throwable {
        createAccountPage.click_create_new_account();
    }
}
