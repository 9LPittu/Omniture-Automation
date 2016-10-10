@RegisteredCheckout
Feature: Checkout - Express user is able to edit data in review page

  Scenario: Checkout - Express user is able to edit shipping address, shipping method and billing data in review page
  	Given User goes to homepage
  	And Handle the Email Capture pop up
  	
  	When click on SIGN IN from header
    And User fills form with multiple user and signs in
    And User bag is cleared
    And User goes to homepage
    
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added 
    
    When User clicks on item bag    
    Then Verify products added matches with products in bag

    And Clicks on checkout
    
    Then Verify user is in review page
    
    When User adds a promo code Test-10p in review page    
    
    #In gold environment, second promo code text box is not displayed.
    # So, not adding verification point for second promo code text box existence as per test case
    # This scenario needs to re-visited once stackable promos toggle is ON
	
    When User edits details for billing
    Then Verify Billing page is displayed

    And Submits payment data in billing page
    Then Verify user is in review page
	
	#Shipping address change
    When User edits details for shipping
    Then Verify Shipping Address page is displayed
	When User selects a different shipping address and continues	
    Then Verifies user is in shipping method page

    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed

    And Submits payment data in billing page
    Then Verify user is in review page
	Then Verify selected shipping address matches review page	
    When User edits details for gifting
    Then Verifies user is in shipping method page
	
	#Billing Method change
    And Submits payment data in billing page
    Then Verify Billing page is displayed
	Then Select different card from the card list	
    
    And Submits payment data in billing page
    Then Verify user is in review page
    
    Then Verify selected billing address matches review page    
	
	#Shipping method change
	Then Select different shipping method on review page		
	Then Verify selected shipping method matches review page
    When User edits details for order
    Then User should be in shopping bag page