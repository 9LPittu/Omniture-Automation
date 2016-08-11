@GuestCheckout-Part1
Feature: User is able to gift receipt to order during Guest checkout

  Scenario: User is able to gift receipt to order during Guest checkout
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User clicks on Clothing in drawer
    And User selects random item from submenu
    And User clicks on random product in category array
    Then Verify a product detail page is displayed

    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    Then Verify that add to bag confirmation message is displayed
    And Verify that the number of items in bag is updated with plus 1

    When User clicks bag in header
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