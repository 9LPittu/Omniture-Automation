@CheckoutRegistered
Feature: Smoke Checkout - Signed user

  Scenario: Smoke Checkout - Signed user
    Given User goes to homepage
    When User closes email capture
    And User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    And This script cleans bag for current user
    And User goes to homepage
    And User hovers on a random category from list
      | Women |
      | Men   |
    And User selects random subcategory array
    And User closes email capture
    And User selects random product from product array
    And Verify product detail page is displayed

    When User selects random color
    And User selects random size
    And User adds product to bag

    When User clicks in bag
    And User clicks in CHECK OUT NOW button

    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    Then Verify billing address display on review page
    Then Verify shipping address display on review page

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible






