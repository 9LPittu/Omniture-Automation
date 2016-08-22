@GuestCheckout-Part1
Feature: Checkout - User is able to gift receipt to order during Guest checkout

  Scenario: User is able to gift receipt to order during Guest checkout
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
    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills QAS shipping data and continues
    Then Verify QAS page is displayed

    When User selects a suggested address and continues
    Then Verify Shipping And Gift Options page is displayed
    
    When User selects a random shipping method
    And User selects gift option and adds message: This is an automated execution message for a gift receipt
    Then Verify gift receipt info message is 'Your message will be printed on the invoice and prices will be omitted.'        
    And User continues to Payment Method page
    
    Then Verify Billing page is displayed    
    Then Verify no additional charges are applied for gift receipt    