@GuestCheckout-Part3
Feature: Expected Shipping Options for special addresses

  Scenario: Expected Shipping Options for APO
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
    And User clicks bag in header
    Then Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills APO shipping data and continues
    Then Verify Shipping And Gift Options page is displayed
    And Verify that this shipping methods are available including Thursday cut
      | method                      | price | text | thursday |
      | Economy (6-8 business Days) | FREE  |      | false    |