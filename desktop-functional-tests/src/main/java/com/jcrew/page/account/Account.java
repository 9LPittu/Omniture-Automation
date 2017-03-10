package com.jcrew.page.account;

import com.jcrew.page.PageObject;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by 9msyed on 8/31/2016.
 */
public abstract class Account extends PageObject {

    public Account(WebDriver driver){
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean shouldRewardDisplayed(String userCategory) {
        boolean expected = false;
        Country c = stateHolder.get("context");
        if (userCategory.equalsIgnoreCase(User.CAT_LOYALTY) && ("us".equalsIgnoreCase(c.getCountry())))
            expected = true;

        return expected;
    }

}
