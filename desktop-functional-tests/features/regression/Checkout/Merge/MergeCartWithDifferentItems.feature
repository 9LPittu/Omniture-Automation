@MergeCheckout
Feature: Merge cart page validation for e-gift card, regular item, backorder item and few left item

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User clicks user button SIGN IN TO MY ACCOUNT in drawer
    And User fills form and signs in
    And This script cleans bag for current user
    And User goes to homepage

  Scenario: Merge cart page validation for e-gift card, regular item, backorder item and few left item

	#Add backordered item to bag
    When User navigates to backordered product
    When User clicks ADD TO BAG button
    Then Verify that add to bag confirmation message is displayed
	
	#Add few left item to bag
    When User navigates to only few left product
    When User clicks ADD TO BAG button
    Then Verify that add to bag confirmation message is displayed
    
    #Add regular item to bag
    When User navigates to regular product    
    When User clicks ADD TO BAG button
    Then Verify that add to bag confirmation message is displayed
    
	#Add e-gift card to bag
	When User expands Let Us Help You drawer
	And Verify Gift Cards link is displayed in Let Us Help You drawer
	When User clicks on Gift Cards link from Let Us Help You drawer
	When User adds e-gift card worth any value to bag
	Then Verify that add to bag confirmation message is displayed
 	
	#sign out 
	When User goes to homepage
	And User clicks on menu
    When User goes back in drawer
    And User clicks user button My Account in drawer
    And User signs out
  	
	#Add item as guest user
    When User goes to homepage
    When User opens menu
    And User clicks on Clothing in drawer
    And User clicks on Shirts & Tops in drawer
    And User clicks on random product in category array
    Then Verify a product detail page is displayed
    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    And User clicks bag in header
    
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