package com.jcrew.steps;

import com.jcrew.page.account.AccountDetail;
import com.jcrew.page.account.IAccountDetail;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.commons.lang.StringEscapeUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by msayed3 on 8/20/2016.
 */
public class AccountDetailSteps extends DriverFactory{

    private IAccountDetail accountDetail = AccountDetail.getAccountDetail(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();

    @Then("Verify user is in account details page")
    public void user_is_in_my_account_page(){
        assertTrue("User is in account detail page", accountDetail.isAccountDetailPage());
    }


    @And("Verify birth field is ([^\"]*)")
    public void verify_birth_field(String isEnabled) {
        assertTrue("Verify birthday field should be " + isEnabled, accountDetail.isBirthField(isEnabled));
    }

    @And("Verify \'([^\"]*)\' copy displayed")
    public void verify_birth_copy_displayed(String expectedCopy) {
        assertEquals("Verify birth copy displayed", expectedCopy, accountDetail.getBirthdayCopy());
    }

    @And("User update ([^\"]*) with ([^\"]*) data")
    public void update_field(String filedName,String updateType){
        accountDetail.updateDetails(filedName, updateType);

    }

    @Then("User information should match My Details page")
    public void sign_in_user_information_should_match_my_details_page() {
        User loggedInUser = (User) stateHolder.get("signedUser");
        User usrFromActDetls  = accountDetail.getUserDetails();

        assertTrue("First name matches", StringEscapeUtils.unescapeHtml(usrFromActDetls.getFirstName()).equalsIgnoreCase(loggedInUser.getFirstName()));
        assertTrue("Last name matches", StringEscapeUtils.unescapeHtml(usrFromActDetls.getLastName()).equalsIgnoreCase(loggedInUser.getLastName()));
        assertTrue("Email matches", usrFromActDetls.getEmail().equalsIgnoreCase(loggedInUser.getEmail()));
        assertTrue("Country matches", usrFromActDetls.getCountry().equalsIgnoreCase(loggedInUser.getCountry()));

    }

    @And("^Verify \'([^\"]*)\' error message displayed for ([^\"]*) field$")
    public void verify_error_message(String errMsgExpected, String fieldLabel) {
    	boolean result = accountDetail.getErrorMessage(fieldLabel).contains(errMsgExpected);
        assertTrue(fieldLabel + " error message should match", result);
    }
    
    @And("Select ([^\"]*) as ([^\"]*) from date")
    public void user_selects_Month(String value, String dateType) {
        accountDetail.selectDate(dateType, value);
    }
    
    @And("click on save button")
    public void click_Save() {
        accountDetail.saveUpdates();

    }
    
    @And("User clicks on ([^\"]*) link in Account detail Page")
    public void click_on_link_from_account_detail_page(String linkText){
        accountDetail.clickLeftNavLinks(linkText);

    }
    
    @Then("User clicks on ([^\"]*) reward link from Account detail Page")
    public void user_clicks_on_reward_link_in_my_account_page(String link) throws Throwable {
        accountDetail.click_reward_link(link);
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

    @And("Verify ([^\"]*) reward link for ([^\"]*) user in account detail page")
    public void reward_link_displayed_for_user(String link, String userCategory) {
        User signedInUser = (User ) stateHolder.get("signedUser");
        assertEquals(link + " link displayed for "+signedInUser.getEmail()+" user and category " + userCategory,
                accountDetail.shouldRewardDisplayed(userCategory),accountDetail.isMenuLinkPresent(link));
    }
}
