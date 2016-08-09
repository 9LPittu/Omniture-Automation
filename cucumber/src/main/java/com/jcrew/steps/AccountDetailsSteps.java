package com.jcrew.steps;

import com.jcrew.page.AccountDetailsPage;
import com.jcrew.page.MyAccountPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Util;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9msyed on 8/8/2016.
 */
public class AccountDetailsSteps extends DriverFactory {

    private final AccountDetailsPage accountDetailsPage = new AccountDetailsPage(getDriver());
    private final MyAccountPage myAccountPage = new MyAccountPage(getDriver());



    @And("^User should be in ([^\"]*) menu link page$")
    public void user_should_be_in_page(String page) throws Throwable {
        assertTrue(Util.getSelectedCountryName() + "User should have been in menu link " + page,
                myAccountPage.isInMenuLinkPage(page));
    }

    @And("^My Details form should display$")
    public void user_details_form_should_display(){
        assertTrue(Util.getSelectedCountryName() + "User should be in user detail page",
                accountDetailsPage.isMyDetailPage());
    }
    @And("^Verify ([^\"]*) is displayed in My details form$")
    public void validate_feild_in_user_detail_form(String fieldName){
        assertTrue(fieldName + " should display in my account details form",
                accountDetailsPage.validateField(fieldName,"isDisplayed"));
    }
}
