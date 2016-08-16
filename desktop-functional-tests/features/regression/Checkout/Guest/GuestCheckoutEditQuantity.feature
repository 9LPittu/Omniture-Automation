@GuestCheckout-Part3
Feature: User is able to edit quantity for item from shopping bag during Guest checkout

  Scenario: User is able to edit quantity for item from shopping bag during Guest checkout
    Given User goes to homepage
    And User closes email capture
    
	#Add Item 1 to bag
    When User opens menu
    And User selects random category from list
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
    
    #Add Item 2 to bag
    When User opens menu
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag

    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag
    
    When User edits quantity of first item from bag
    Then Verify items quantity and prices
    Then Verify Order Subtotal is updated when item quantity is changed
    
    When User edits first added item from bag    
    And Verify product detail page is displayed
    
    Then Verify UPDATE BAG button is displayed    
    When Update Bag button is pressed
    
    When User clicks in bag
    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag