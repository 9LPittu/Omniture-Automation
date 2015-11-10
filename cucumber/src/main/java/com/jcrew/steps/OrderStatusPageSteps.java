package com.jcrew.steps;

import com.jcrew.page.OrderStatusPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class OrderStatusPageSteps extends DriverFactory {


    @And("^Verify user is on order status page$")
    public void verify_user_is_on_order_status_page() throws Throwable {
        OrderStatusPage orderStatusPage = new OrderStatusPage(getDriver());
        assertTrue("User should have been in order status page", orderStatusPage.isOrderStatusPage());
    }
}
