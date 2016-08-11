@GuestCheckout-Part2
Feature: User is able to remove item from shopping bag during Guest checkout

  Scenario: Guest user is able to remove item from shopping bag
    Given User goes to homepage
    And User closes email capture
    
    #Add Item 1 to bag
    When User opens menu
    And User clicks on Clothing in drawer
    And User selects random item from submenu
    And User clicks on random product in category array
    Then Verify a product detail page is displayed
    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    
    #Add Item 2 to bag
    When User opens menu
    And User selects random item from submenu
    And User clicks on random product in category array
    Then Verify a product detail page is displayed
    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    
    And User clicks bag in header
    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag
    
    When User removes first item from bag
    Then Verify products added matches with products in bag
    Then Verify Order Subtotal is updated when item is removed