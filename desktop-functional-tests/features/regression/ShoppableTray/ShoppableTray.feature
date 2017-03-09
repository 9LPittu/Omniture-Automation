@ShoppableTray @HighLevel
Feature: Shopplbale tray features

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
   

  Scenario: Verify ability to add to cart
   And Add all products to cart
   Then Verify all products are in cart