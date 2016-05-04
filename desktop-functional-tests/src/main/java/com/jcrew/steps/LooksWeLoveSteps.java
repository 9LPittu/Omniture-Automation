package com.jcrew.steps;

import com.jcrew.page.LooksWeLove;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

public class LooksWeLoveSteps extends DriverFactory{

    public final LooksWeLove looksWeLove = new LooksWeLove(getDriver());

    @Then("^User selects random shop the look page for ([^\"]*)$")
    public void user_Selects_Random_Shop_The_Look_Page(String type){
        looksWeLove.selectRandomShopThisLook(type);
    }
}
