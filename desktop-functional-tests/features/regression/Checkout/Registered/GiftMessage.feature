@RegisteredCheckout @HighLevel
Feature: Checkout - Registered user is able to add a gift message

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills form with no default user and signs in
    And This script cleans bag for current user
    And User goes to homepage
	And User signs out using header
	
  Scenario: Checkout - Registered user is able to add a gift message
    Given User goes to homepage
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

    When User clicks in bag
    Then Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    And User signs in with no default user and checks out
    Then Verify select shipping address page is displayed

    When User selects a shipping address and continues
    Then Verify Shipping And Gift Options page is displayed

    When User selects a random shipping method
    And User selects gift option and adds message: This is an automated execution message for a gift
    And User continues to Payment Method page
    Then Verify Billing page is displayed

    When User adds new card
    Then Verify Billing Payment page is displayed

    When User fills billing payment with mastercard and continues
    Then Verify user is in review page

    When User edits details for billing
    Then Verify Billing page is displayed
    And Verify card has been added

    When User edits recently added card
    Then Verify Billing Payment page is displayed

    When User edits billing payment information and continues
    Then Verify Billing Payment page is displayed
    And Verify card has been edited

    When User removes Mastercard card
    Then Verify card has been removed

    When User continues to review page
    Then Verify user is in review page

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible
    And Verify Gift message is This is an automated execution message for a gift