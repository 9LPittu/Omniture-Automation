package com.jcrew.steps;

import com.jcrew.page.Footer;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 8/31/2015.
 */
public class FooterSteps extends DriverFactory {
    private Footer footer = new Footer(getDriver());

    @And("^Verify ([^\"]*) footer link is displayed$")
    public void verify_footer_link_is_displayed(String footerLink) throws Throwable {
        assertTrue(footerLink + " should have been present", footer.isFooterLinkPresent(footerLink));
    }

    @And("^Verify ([^\"]*) footer links order is valid, ignore ([^\"]*)$")
    public void verify_footer_links_order_is_valid(String footerLinks, String ignoredLinks) throws Throwable {
        String[] footerLinksArray = footerLinks.split(",");

        List<String> footerLinksList = new ArrayList<>();
        String[] ignoredLinksArray = ignoredLinks.split(",");
        List<String> ignoredLinksList = new ArrayList<>();

        for (String ignoredLink : ignoredLinksArray) {
            ignoredLinksList.add(ignoredLink.trim());
        }
        for (String footerLink : footerLinksArray) {
            String footerLinkTrimmed = footerLink.trim();
            footerLinksList.add(footerLinkTrimmed);
        }

        List<String> footerHeaderNames = footer.getFooterHeaderNames();
        footerHeaderNames.removeAll(ignoredLinksList);
        assertEquals("Footer Links order is not the expected", footerLinksList, footerHeaderNames);

    }
}
