package com.jcrew.steps;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.jcrew.page.ShoppableTrayPage;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.When;

public class ShoppableTraySteps extends DriverFactory {

	 private StateHolder stateHolder = StateHolder.getInstance();
	 ShoppableTrayPage shoppableTrayPage = new ShoppableTrayPage(getDriver());

	 @When("^User selects random MPDP for shoppableTray$")
	 public void select_random_item_for_shoppableTray() {
		
	  shoppableTrayPage.selectTheRandomProductForShoppableTray();
	    	
	 }
	 
	 @When("^Verify SHOP THE LOOK title is displayed on top$")
	 public void verifyShopTheLookTitle(){
		 assertEquals("Title matches with shoppabletray title", shoppableTrayPage.verifyShoppableTrayTitleTemp(),
				 shoppableTrayPage.verifyShoppableTrayTitle());
		 
	 }
	 
	 @When("^Verify the itmes count in shoppableTray$")
	 public void itemsCountInTitle(){
	 shoppableTrayPage.shoppableTrayItemcount();
	 
	 }
	 
	 @When("^Verify the items counts matches with Carousel items$")
	 public void verifyItemsCount(){
	  assertEquals("Itmes count matches with Carousel items count",
			  shoppableTrayPage.shoppableTrayItemcount(), shoppableTrayPage.itemsCountInCarousel());
	 }
	 @When("^Verify the ecach product has name item number colour size$")
	 public void productDetails(){
		 
		 shoppableTrayPage.verifyAllProductDetails();
	 }
	
}
