@GuestCheckout3
Feature: Checkout - Guest user is able to edit data in review page

  Scenario: Checkout - Guest user is able to edit data in review page
    Given User goes to homepage
    And User closes email capture
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

    When User clicks in bag
    Then Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills shipping data and continues
    Then Verify Shipping And Gift Options page is displayed

    When User selects a random shipping method and continues
    Then Verify Billing page is displayed

    When User fills payment method as guest and continues
    Then Verify user is in review page

    When User edits details for billing
    Then Verify Billing page is displayed

    When User continues to review page
    Then Verify user is in review page

    When User edits details for shipping
    Then Verify Shipping Page is displayed

    When User continues to Shipping and Gift Options page
    Then Verify Shipping And Gift Options page is displayed

    When User continues to Payment Method page
    Then Verify Billing page is displayed

    When User continues to review page
    Then Verify user is in review page

    When User edits details for gifting
    Then Verify Shipping And Gift Options page is displayed

    When User continues to Payment Method page
    Then Verify Billing page is displayed

    When User continues to review page
    Then Verify user is in review page

    When User edits details for order
    Then Verify shopping bag is displayed