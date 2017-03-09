@ShoppableTray @HighLevel
Feature: Shoppable tray features

  Background:
    Given User goes to homepage
    And User closes email capture    
   When User hovers on a random category from list
    	|Men|
   When User selects Suits subcategory array 	
   And User selects random tray from available categories	  

  Scenario: ShoppableTray from sub category page
   Then Verifies header text is SHOP THE LOOK
   Then Verifies initial multiple pdp page state
   And Verify the items counts matches with Carousel items
   Then Verify every product contains name, image, price, color and size
   Then Verify every product contains SIZE & FIT 

  Scenario: Verify ability to add to cart
   And Add all products to cart
   Then Verify all products are in cart
   
  Scenario: Verify that externalProductCodes parameter supports lowercase
    And Copy URL and use externalProductCodes in lower case to access tray
    Then Verify that all products match with original URL
    Then Verifies multiple pdp page contains pagination
    Then Verifies initial multiple pdp page state 