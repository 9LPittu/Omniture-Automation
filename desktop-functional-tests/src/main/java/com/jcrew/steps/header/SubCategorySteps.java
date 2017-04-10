package com.jcrew.steps.header;

import com.jcrew.page.header.SubCategory;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 3/2/17.
 */
public class SubCategorySteps extends DriverFactory {
    private SubCategory subCategory = new SubCategory(getDriver());

    @When("User selects ([^\"]*) subcategory array")
    public void user_selects_specific_subcategory_array(String subcategory) {
        if ("random".equalsIgnoreCase(subcategory)) {
            subCategory.selectSubCategory();
        } else {
            subCategory.selectSubCategory(subcategory);
        }
    }

    @When("User selects ([^\"]*) from our shops")
    public void user_selects_shop(String shop) {
        subCategory.clickShop(shop);
    }

    @Then("Verify shop ([^\"]*) is available")
    public void shop_available(String shop) {
        assertTrue("Shop " + shop + " is avaliable", subCategory.getShops().contains(shop));
    }
}
