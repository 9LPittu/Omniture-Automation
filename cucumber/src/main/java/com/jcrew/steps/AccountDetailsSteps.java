package com.jcrew.steps;

import com.jcrew.page.AccountDetailsPage;
import com.jcrew.page.MyAccountPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Util;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    @And("^Click on Change Password in my details form$")
    public void click_on_change_password_link(){
        accountDetailsPage.clickChangePassword();
    }


    @And("^User selects ([^\"]*) from my details dropdown$")
    public void user_selects_from_drop_down_in_my_detail_page(String value){
        accountDetailsPage.selectFromList(value);

    }
    @And("validate ([^\"]*) option ([^\"]*) for ([^\"]*) user")
    public void verify_option_exists_for_user(String option,String visible){
        if("notVisible".equalsIgnoreCase(visible)){
            assertFalse(option +" option is not available to user ",accountDetailsPage.isOptionAvailable(option));
        }else{
            assertTrue(option +" option is available for user ",accountDetailsPage.isOptionAvailable(option));
        }

    }

    @And("Verify birthdate is enabled")
    public void verify_birthfields_is_enabled(){
        assertFalse("Birth fields is enabled ",accountDetailsPage.isBirthFieldsDisabled());
    }
    @And("Verify birthdate is disabled")
    public void verify_birthfields_is_disabled(){
        assertTrue("Birth fields is disabled ",accountDetailsPage.isBirthFieldsDisabled());
    }

    @And("Update ([^\"]*) to invalid and verify \'([^\"]*)\' error message")
    public void verify_error_message(String fieldLabel,String errMsgExpected){
        assertEquals(fieldLabel + " error message should match",errMsgExpected,accountDetailsPage.getErrorMessage(fieldLabel));

    }
    @And("Verify \'([^\"]*)\' error message should display for birth field")
    public void verify_birth_error_message(String errMsgExpected){
        assertEquals("Birth error message should match",errMsgExpected,accountDetailsPage.getBirthErrorMessage());

    }
    @And("Update ([^\"]*) in my details form")
    public void user_updates_my_details(String fieldLabel){
        accountDetailsPage.updateDetails(fieldLabel);
    }
    @And("click on save button")
    public void click_Save(){
        accountDetailsPage.saveUpdates();

    }
    @And("verify confirmation message displayed")
    public void validate_confirmation_msg(){
        assertEquals("","Your information has been updated.",accountDetailsPage.getConfirmatonMsg());
    }

    @And("Select ([^\"]*) as ([^\"]*) from date")
    public void user_selects_Month(String value,String dateType){
        accountDetailsPage.selectDate(dateType,value);
    }

}
