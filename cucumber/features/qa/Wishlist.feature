@Wishlist
Feature: Ability to link to PDP from Wishlist

  Background:
    Given User is on homepage
    And Goes to sign in page
    When User enters wishlist@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    And User should be in wishlist page
    Then Click on product item2108 to display properties
    And Verify color is DEEP NAVY in wishlist page
    And Verify size is MEDIUM in wishlist page
    And Verify quantity is 1 in wishlist page
    Then Click on home icon menu
    And Click on edit wishlist product

  Scenario: User should see corresponding PDP
    And User is in product detail page
    Then Verify color DEEP NAVY is selected
    And Verify size MEDIUM is selected
    And Verify 1 items are specified as quantity

  Scenario: Corresponding PDP should be editable
    And User is in product detail page
    Then Verify color DEEP NAVY is selected
    And Verify size MEDIUM is selected
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    Then Color HTHR IVORY is selected by user
    And Size LARGE is selected by user
    And Quantity 3 is selected by user
    Then Verify color HTHR IVORY is selected
    And Verify size LARGE is selected
    And Verify 3 items are specified as quantity
