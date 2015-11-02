package com.jcrew.steps;

import com.jcrew.page.JcrewGiftCardPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class JcrewGiftCardPageSteps extends DriverFactory {

    @And("^Verify user is on the j.crew gift card page$")
    public void verify_user_is_on_the_j_crew_gift_card_page() throws Throwable {
        JcrewGiftCardPage giftCardPage = new JcrewGiftCardPage(getDriver());
        assertTrue("User should have been in Jcrew gift card page", giftCardPage.isJcrewGiftCardPage());
    }

}
