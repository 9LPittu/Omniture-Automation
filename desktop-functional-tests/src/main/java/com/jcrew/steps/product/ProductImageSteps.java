package com.jcrew.steps.product;

import com.jcrew.page.product.ProductImage;
import com.jcrew.page.product.ProductThumbnails;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 3/30/17.
 */
public class ProductImageSteps extends DriverFactory {
    private ProductImage image = new ProductImage(getDriver());
    private ProductThumbnails thumbnails = new ProductThumbnails(getDriver());

    @When("User clicks selected image")
    public void click_selected_image() {
        image.clickSelectedImage();
    }

    @When("User hovers selected image")
    public void hover_selected_image() {
        image.hoverSelectedImage();
    }

    @Then("Verify selected image is zoomed")
    public void selected_image_zoomed() {
        assertTrue("Image is zoomed", image.isSelectedImageZoomed());
    }

    @Then("Verify selected image is not zoomed")
    public void selected_image_not_zoomed() {
        assertFalse("Image is not zoomed", image.isSelectedImageZoomed());
    }

    @Then("Verify zoom modal is displayed")
    public void zoom_modal_is_displayed() {
        assertTrue("Zoom modal is displayed", image.isModalDisplayed());
    }

    @Then("Verify product has thumbnail selected by default")
    public void has_default_thumbnail() {
        assertTrue("Has selected thumbnail", thumbnails.hasSelectedThumbnail());
    }

    @Then("Verify only non EIEC shots are displayed as thumbnails")
    public void non_eiec() {
        int size = thumbnails.getThumbnailsListSize();

        for(int i = 0; i < size; i++) {
            assertTrue("Only non EIEC images are displayed",
                    thumbnails.getShotType(i).equalsIgnoreCase("EIEC") & !thumbnails.isThumbnailDisplayed(i));
        }
    }
}
