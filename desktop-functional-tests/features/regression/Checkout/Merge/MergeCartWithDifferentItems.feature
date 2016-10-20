@MergeCheckout
Feature: Checkout - Merge cart page validation for e-gift card, regular item, backorder item and few left item

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills form and signs in
    And This script cleans bag for current user
    And User goes to homepage

  Scenario: Checkout - Merge cart page validation for e-gift card, regular item, backorder item and few left item

	#Add backordered item to bag
    When User navigates to backordered product    
    And User adds product to bag
	
	#Add few left item to bag
    When User navigates to only few left product    
    And User adds product to bag
    
    #Add regular item to bag
    When User navigates to regular product   
    And User adds product to bag
    
	#Add e-gift card to bag
	Then Verify The J.Crew Gift Card link is displayed under Our Cards accordion in footer
	When User clicks on The J.Crew Gift Card link under Our Cards accordion in footer	
	When User adds e-gift card worth any value to bag
	 	
	#sign out 
	And User signs out using header
  	
	#Add item as guest user
    When User goes to homepage
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
    And User clicks in bag
    
    #checkout and sign in as previous user
    When User clicks in CHECK OUT NOW button    
    And User signs in and checks out
    
    #Merge cart page validation
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And Verify 'Save to Wishlist & Continue' button is available in Merge Cart Page    
    And Verify 'Add items to bag & Review Order' button is available in Merge Cart Page
    
    #item status verification
    Then Verify the message as 'IN STOCK - ONLY A FEW LEFT' for the few left item in Merge Cart page
    Then Verify the message as 'IN STOCK' for the regular item in Merge Cart page    
    Then Verify the message for the backordered item in Merge Cart page    
    Then Verify e-gift card details in Merge Cart page