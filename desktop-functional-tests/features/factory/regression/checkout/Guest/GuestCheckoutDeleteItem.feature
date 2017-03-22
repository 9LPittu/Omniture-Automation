@GuestCheckout6
Feature: Checkout - User is able to remove item from shopping bag during Guest checkout

  Scenario: Checkout - Guest user is able to remove item from shopping bag
    Given User goes to homepage
    And User closes email capture

    #Add Item 1 to bag
    And User hovers on a random category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed

    When User selects random color
    And User selects random size
    And User adds product to bag

    #Add Item 2 to bag
    When User hovers on a random category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed

    When User selects random color
    And User selects random size
    And User adds product to bag

    When User clicks in bag

    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag

    When User removes first item from bag
    Then Verify products added matches with products in bag
    Then Verify Order Subtotal is updated when item is removed