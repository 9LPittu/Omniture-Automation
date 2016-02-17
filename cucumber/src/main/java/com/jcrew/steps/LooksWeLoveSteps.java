package com.jcrew.steps;

import com.jcrew.page.LooksWeLovePage;
import com.jcrew.util.DriverFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

public class LooksWeLoveSteps extends DriverFactory{

    public final LooksWeLovePage looksWeLove = new LooksWeLovePage(getDriver());

    @Then("^User selects random shop the look page$")
    public void user_Selects_Random_Shop_The_Look_Page(){
        looksWeLove.selectRandomShopThisLook();
    }
}
