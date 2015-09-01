package com.jcrew.steps;

import com.jcrew.page.BrowseOurStyleGuidePage;
import com.jcrew.util.DriverFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class BrowseOurStyleGuidePageSteps extends DriverFactory{
    BrowseOurStyleGuidePage browseOurStyleGuidePage = new BrowseOurStyleGuidePage(getDriver());

    @And("^Verify user is on browse our style guide page$")
    public void verify_user_is_on_browse_our_style_guide_page() throws Throwable {

        assertTrue("User should be on browse our style guide page",browseOurStyleGuidePage.isBrowseOurStyleGuidePage());
    }
}
