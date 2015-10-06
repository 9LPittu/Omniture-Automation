package com.jcrew.steps;

import com.jcrew.page.JcrewCreditCardPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class JcrewCreditCardPageSteps extends DriverFactory {



    @And("^Verify user is on the j.crew credit card page$")

    public void verify_user_is_on_the_j_crew_credit_card_page() throws Throwable {
        JcrewCreditCardPage creditCardPage = new JcrewCreditCardPage(getDriver());
        assertTrue("User should be on Jcrew Credit Card Page", creditCardPage.isJcrewCreditCardPage());
    }
}