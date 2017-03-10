package com.jcrew.steps.header;

import com.jcrew.page.header.SubCategory;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by ngarcia on 3/2/17.
 */
public class SubCategorySteps extends DriverFactory {
    private SubCategory subCategory = new SubCategory(getDriver());

    @When("User selects ([^\"]*) subcategory array")
    public void user_selects_specific_subcategory_array(String subcategory) {
        if("random".equalsIgnoreCase(subcategory)) {
            subCategory.selectSubCategory();
        } else {
            subCategory.selectSubCategory(subcategory);
        }
    }
}
