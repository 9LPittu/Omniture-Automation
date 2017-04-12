@ShoppingBag @HighLevel
Feature: Checkout - Editing items from shopping bag

Background:
    Given User goes to homepage
    And User closes email capture
	And User hovers on a random category from list
    	|Women|
    	|Men|
    	|Girls|
    	|Boys|
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
Scenario: Checkout - Multiple shopping bag functions
    When User goes to homepage
    And User clicks on sale link from top nav
    And User selects random sale category
    When User selects random product from array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User searches for a random search term
    And User selects random product from array
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag
    Then Verify products added matches with products in bag
    And Verify all products have edit and remove buttons

    #User is able to edit quantity from shopping bag
    When User edits quantity of first item from bag
    Then Verify products added matches with products in bag
    And Verify items quantity and prices

    #User is able to remove item from shopping bag
    When User removes first item from bag
    Then Verify products added matches with products in bag

    #User is able to edit item from shopping bag
    When User edits last item from bag    
    Then Verify that page contains a selected color    
    Then Verify that page contains a selected size
    
    Then Verify Update Bag button is displayed
    
    When User selects random color
    And User selects random size
    When Update Bag button is pressed
    
    When User clicks in bag
    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag
    Then Verify edited item is displayed first in shopping bag
    
  Scenario: Checkout -shopping bag functions
    When User goes to homepage
    When User searches for a random search term
    And User selects random product from array
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag
    Then Verify products added matches with products in bag
	When User adds a promo code Test-invalid
	Then Verify promo message says: The promotion code you entered is not valid or has expired. Please try the code again or call 800 562 0258 for help.
	When User adds a promo code Test-10p
    Then Verify promo details contains: 10% off (no min)
    Then Verify second promo text box is displayed in promo section
    When User adds promo code Test-20p in shopping bag page
    Then Verify promo message says: These promotion codes cannot be combined.
    When User adds a promo code Stack10p
    Then Verify user is not allowed to add thrid promo