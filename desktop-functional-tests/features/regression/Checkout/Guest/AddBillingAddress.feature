@GuestCheckout-Part1
Feature: Guest user is able to add billing address

  Scenario: Guest user is able to add billing address
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