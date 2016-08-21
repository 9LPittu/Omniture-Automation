package com.jcrew.steps;

import com.jcrew.page.AccountDetail;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

/**
 * Created by msayed3 on 8/20/2016.
 */
public class AccountDetailSteps extends DriverFactory{

    AccountDetail accountDetail = new AccountDetail(getDriver());

    @Then("Verify user is in account details page")
    public void user_is_in_my_account_page(){
        assertTrue("User is in account detail page", accountDetail.isAccountDetailPage());
    }
}
