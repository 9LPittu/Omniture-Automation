@RegisteredCheckout
Feature: Registered user is able to add a gift message

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User clicks user button SIGN IN TO MY ACCOUNT in drawer
    And User fills form with no default user and signs in
    And This script cleans bag for current user
    And User goes to homepage

    When User opens menu
    And User clicks user button My Account in drawer
    And User signs out

  Scenario: Registered user is able to add a gift message
    Given User goes to homepage
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