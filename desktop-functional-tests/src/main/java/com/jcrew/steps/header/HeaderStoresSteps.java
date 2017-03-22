package com.jcrew.steps.header;

import com.jcrew.page.header.HeaderStores;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by ngarcia on 3/22/17.
 */
public class HeaderStoresSteps extends DriverFactory {
    private HeaderStores stores = new HeaderStores(getDriver());

    @When("User clicks on stores using header")
    public void user_clicks_stores_from_header(){
        stores.clickStores();
    }
}
