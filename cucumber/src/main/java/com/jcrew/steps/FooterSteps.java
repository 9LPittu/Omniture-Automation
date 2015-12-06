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
}
