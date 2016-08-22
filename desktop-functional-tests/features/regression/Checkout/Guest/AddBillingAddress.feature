@GuestCheckout-Part1
Feature: Checkout - Guest user is able to add billing address

  Scenario: Guest user is able to add billing address
    Given User goes to homepage
    And User closes email capture
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
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills shipping data and continues
    Then Verify Shipping And Gift Options page is displayed

    When User selects a random shipping method and continues
    Then Verify Billing page is displayed

    When User adds new billing address
    Then Verify Billing Address page is displayed

    When User fills billing address and continues
    Then Verify Billing page is displayed

    When User fills payment method as guest and continues
    Then Verify user is in review page
    And Verify added billing address matches review page

    When User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number