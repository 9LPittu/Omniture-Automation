package com.jcrew.steps;

import com.jcrew.page.AccountDetail;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;
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


    @And("Verify birth field is ([^\"]*)")
    public void verify_birth_field(String isEnabled) {
        assertTrue("Verify birthday filed should be " + isEnabled, accountDetail.isBirthField(isEnabled));
    }
    @And("User update ([^\"]*) with ([^\"]*) data")
    public void update_field(String filedName,String updateType){
        accountDetail.updateDetails(filedName, updateType);

    }
    @And("Verify \'([^\"]*)\' error message displayed for ([^\"]*) field")
    public void verify_error_message(String errMsgExpected, String fieldLabel) {
        assertEquals(fieldLabel + " error message should match", errMsgExpected, accountDetail.getErrorMessage(fieldLabel));

    }
    @And("Select ([^\"]*) as ([^\"]*) from date")
    public void user_selects_Month(String value, String dateType) {
        accountDetail.selectDate(dateType, value);
    }
    @And("click on save button")
    public void click_Save() {
        accountDetail.saveUpdates();

    }
    @And("verify confirmation message displayed")
    public void validate_confirmation_msg() {
        assertEquals("", "Your information has been updated.", accountDetail.getConfirmatonMsg());
    }
    @And("^Click on Change Password in my details form$")
    public void click_on_change_password_link() {
        accountDetail.clickChangePassword();
    }
    @And("Enter old and new password details")
    public void change_password() {
        accountDetail.fillChangePasswordFileds();
    }
}
