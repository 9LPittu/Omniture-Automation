@RegisteredCheckout
Feature: Express user is able to edit data in review page

  Scenario: Express user is able to edit shipping address, shipping method and billing data in review page
  	Given User goes to homepage
  	And User closes email capture
  	When User clicks on sign in using header
    And User fills form with multiple user and signs in
    And This script cleans bag for current user
    And User goes to homepage
    
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

    When User clicks in bag
    Then Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    
    Then Verify user is in review page
    
    When User adds a promo code Test-10p in review page
    
    #In gold environment, second promo code text box is not displayed.
    # So, not adding verification point for second promo code text box existence as per test case
    # This scenario needs to re-visited once stackable promos toggle is ON
	
    When User edits details for billing
    Then Verify Billing page is displayed

    When User continues to review page
    Then Verify user is in review page
	
	#Shipping address change
    When User edits details for shipping
    Then Verify select shipping address page is displayed
	When User selects a different shipping address and continues
    Then Verify Shipping And Gift Options page is displayed

    When User continues to Payment Method page
    Then Verify Billing page is displayed

    When User continues to review page
    Then Verify user is in review page
	Then Verify selected shipping address matches review page
    When User edits details for gifting
    Then Verify Shipping And Gift Options page is displayed
	
	#Billing Method change
    When User continues to Payment Method page
    Then Verify Billing page is displayed
	Then Select different card from the card list
    
    When User continues to review page
    Then Verify user is in review page
    
    Then Verify selected billing address matches review page
	
	#Shipping method change
	Then Select different shipping method on review page
	Then Verify selected shipping method matches review page
    When User edits details for order
    Then Verify shopping bag is displayed