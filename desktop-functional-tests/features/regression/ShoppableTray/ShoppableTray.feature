@ShoppableTray @HighLevel
Feature: PDP Layout

  Background:
    Given User goes to homepage
    And User closes email capture    
   When User hovers on a random category from list
    	|Women|
    When User selects Looks we Love subcategory array 	
	And Copy URL and use externalProductCodes in lower case to access tray
  Scenario: ShoppableTray from sub category page
   
	And User selects random MPDP for shoppableTray
	When Verify SHOP THE LOOK title is displayed on top
	Then Verifies initial multiple pdp page state
	
	And Verify the itmes count in shoppableTray
	
	And Verify the items counts matches with Carousel items

	Then Verify every product contains name, image, price, color and size
	Then Verify every product contains product, size and fit and review drawers
	 
