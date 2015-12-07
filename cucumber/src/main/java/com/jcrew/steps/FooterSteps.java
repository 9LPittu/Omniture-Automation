package com.jcrew.steps;

import com.jcrew.page.Footer;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FooterSteps extends DriverFactory {
    private final Footer footer = new Footer(getDriver());

    @And("^Verify ([^\"]*) footer link is displayed$")
    public void verify_footer_link_is_displayed(String footerLink) throws Throwable {
        assertTrue(footerLink + " should have been present", footer.isFooterLinkPresent(footerLink));
    }

    @Then("^Click on footer link ([^\"]*)$")
    public void click_on_footer_link(String footerLink) throws Throwable {
        footer.click_on(footerLink);

    }

    @And("^Verify ([^\"]*) footer sub text is displayed for ([^\"]*) footer link$")
    public void verify_footer_sub_text_is_displayed(String subText, String footerLink) throws Throwable {
        assertEquals("sub text is not the same", subText, footer.getFooterSubText(footerLink));
    }
    @And("^([^\"]*) sublink is displayed$")
    public void verify_sublink_is_displayed(String sublink) {
        assertTrue(sublink+" should be displayed", footer.isSubLinkDisplayed(sublink));
    }

    @And("^Click on sublink ([^\"]*) from ([^\"]*) footer link$")
    public void click_on_footer_sub_link(String footerSubLink, String footerLink) throws Throwable {
        footer.click_sublink_from(footerSubLink, footerLink);
    }

    @And("^Verify ([^\"]*) footer header legend is displayed$")
    public void verify_footer_header_is_displayed(String footerHeader) throws Throwable {
        assertEquals("Footer header should have been the same", footerHeader,
                footer.getFooterHeaderLegend());
    }

    @Then("^Click on ([^\"]*) bottom link from footer$")
    public void click_on_bottom_link_from_footer(String bottomLink) throws Throwable {
        footer.click_bottom_link(bottomLink);
    }

    @Then("^([^\"]*) header from footer is visible$")
    public void click_con_header_from_footer(String text) throws Throwable {
        assertTrue("Contact us header is visible", footer.isTopHeaderVisible(text));
    }

    @And("^([^\"]*) icon is displayed$")
    public void verify_icon_and_text_is_displayed(String icon) {
        assertTrue(icon+ "should be displayed+", footer.isIconAndTextDisplayed(icon));

    }

    @And("Verify email field is displayed$")
    public void verify_email_field_is_displayed() {
        assertTrue("Email field should be displayed", footer.isEmailFieldDisplayed());
    }

    @And("Verify ([^\"]*) icon is displayed under Get To Know Us section")
    public void verify_social_icons_are_displayed(String socialIcon) {
        assertTrue("Social network icon should be displayed", footer.isSocialIconDisplayed(socialIcon));
    }
    
    @Then("^user should see email subscription field under LIKE BEING FIRST section$")
    public void verify_email_subscription_displayed_under_like_being_first_section(){
    	assertTrue("Email subscription field under LIKE BEING FIRST section", footer.isEmailDisplayedUnderLikeBeingFirstSection());
    }
    
    @And("^user should see default text in email field as \'([^\"]*)\'$")
    public void verify_default_text_in_email_field(String defaultText){
    	assertTrue("Default text in the email field should be " + defaultText, footer.isEmailFieldMatchesWithDefaultText(defaultText));
    }
    
    @Then("^enter email address as \"([^\"]*)\" in email field$")
    public void enter_email_address_in_footer_email_field(String emailAddress){
    	footer.enterEmailAddressInFooterEmailField(emailAddress);
    }
    
    @And("^click on signup button in footer$")
    public void click_signup_button_in_footer(){
    	footer.clickSignUpButtonInFooter();
    }
    
    @Then("^user should see message as \"([^\"]*)\"$")
    public void message_displayed_footer_signup(String message) throws InterruptedException{
    	assertTrue("Message should be displayed as " + message, footer.isMessageDisplayedCorrectlyDuringFooterSignUp(message));
    }
    
    @Then("^user should see Ship To section in footer$")
    public void verify_ship_to_section_is_displayed(){
    	assertTrue("SHIP TO section should be displayed in the footer",footer.isShipToSectionDisplayed());
    }
    
    @And("^verify country name is displayed in the ship to section of footer$")
    public void verify_country_name_displayed_in_footer(){
    	assertTrue("Country name should be displayed in the footer",footer.isCountryNameDisplayedInFooter());
    }
    
    @And("^verify change link is displayed in the ship to section of footer$")
    public void verify_change_link_displayed_in_footer(){
    	assertTrue("Change link should be displayed in the footer",footer.isChangeLinkDisplayedInFooter());
    }
    
    @Then("^click on change link from footer$")
    public void click_change_link_in_footer(){
    	footer.clickChangeLinkInFooter();
    }
    
    @And("^select country as \"([^\"]*)\"$")
    public void select_country_from_footer(String country){
    	footer.selectCountry(country);
    }
    
    @Then("^user should see \"([^\"]*)\" in footer$")
    public void verify_country_name_in_footer(String country){
    	assertTrue("Country name should be displayed as " + country, footer.isChangedCountryNameDsiplayedInFooter(country));
    }
}