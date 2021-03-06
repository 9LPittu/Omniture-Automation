package com.jcrew.steps;

import com.jcrew.page.AccountDetailsPage;
import com.jcrew.page.MyAccountPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

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
    private final StateHolder stateHolder = StateHolder.getInstance();


    @And("^User should be in ([^\"]*) menu link page$")
    public void user_should_be_in_page(String page) throws Throwable {
        assertTrue(Util.getSelectedCountryName() + "User should have been in menu link " + page,
                myAccountPage.isInMenuLinkPage(page));
    }

    @And("^User navigates to my detail form$")
    public void user_navigates_to_my_details_form() {
        assertTrue(Util.getSelectedCountryName() + "User navigated back to mydetail page", accountDetailsPage.toMyDetailPage());
    }

    @And("^My Details form should display$")
    public void user_details_form_should_display() {
        assertTrue(Util.getSelectedCountryName() + "User should be in user detail page",
                accountDetailsPage.isMyDetailPage());
    }

    @And("^Click on Change Password in my details form$")
    public void click_on_change_password_link() {
        accountDetailsPage.clickChangePassword();
    }

    @And("Enter old and new password details")
    public void change_password() {
        accountDetailsPage.fillChangePasswordFileds();
    }

    @And("^User selects ([^\"]*) from my details dropdown$")
    public void user_selects_from_drop_down_in_my_detail_page(String value) {
        accountDetailsPage.selectFromList(value);

    }

    @And("Verify ([^\"]*) reward link for ([^\"]*) user in My details dropdown")
    public void reward_link_displayed_in_dropdown_for_user(String link, String userCategory) {
    	String user;
    	if(stateHolder.hasKey("sidecarusername")) {
    		user = (String) stateHolder.get("sidecarusername");
    	}	else {
    		PropertyReader reader = PropertyReader.getPropertyReader();
    		user = reader.getProperty("checkout.signed.in.username");
    	}
        assertTrue(link + " link displayed for user category " + userCategory + " and user " + user , accountDetailsPage.verifyRewardLink(link, userCategory));
    }

    @And("Verify birth field is ([^\"]*)")
    public void verify_birth_field(String isEnabled) {
        assertTrue("Verify birthday filed should be " + isEnabled, accountDetailsPage.isBirthField(isEnabled));
    }

    @And("Verify \'([^\"]*)\' copy displayed")
    public void verify_birth_copy_displayed(String expectedCopy) {
        assertEquals("Verify birth copy displayed", expectedCopy, accountDetailsPage.getBirthdayCopy());
    }

    @And("Verify \'([^\"]*)\' error message displayed for ([^\"]*) field")
    public void verify_error_message(String errMsgExpected, String fieldLabel) {
        assertEquals(fieldLabel + " error message should match", errMsgExpected, accountDetailsPage.getErrorMessage(fieldLabel));

    }

    @And("Update ([^\"]*) with ([^\"]*) data")
    public void user_updates_my_details(String fieldLabel, String updateType) {
        accountDetailsPage.updateDetails(fieldLabel, updateType);
    }


    @And("click on save button")
    public void click_Save() {
        accountDetailsPage.saveUpdates();

    }

    @And("verify confirmation message displayed")
    public void validate_confirmation_msg() {
        assertEquals("", "Your information has been updated.", accountDetailsPage.getConfirmatonMsg());
    }

    @And("Select ([^\"]*) as ([^\"]*) from date")
    public void user_selects_Month(String value, String dateType) {
        accountDetailsPage.selectDate(dateType, value);
    }

}
